package com.air.company.spring.service.defalt;

import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.entity.Ticket;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.repository.TicketsRepository;
import com.air.company.spring.service.convertors.TicketsConverter;
import com.air.company.spring.service.interfaces.FlightsService;
import com.air.company.spring.service.interfaces.SeatsService;
import com.air.company.spring.service.interfaces.TicketsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultTicketService implements TicketsService {
    private final TicketsConverter ticketsConverter;
    private final TicketsRepository ticketsRepository;
    private final FlightsService flightsService;
    private final SeatsService seatsService;

    @Override
    public TicketsDto saveTicket(TicketsDto ticketsDto) throws ValidationException {
        validateTicketDto(ticketsDto);
        Ticket savedTicket;
        if (ticketsDto.getActive() == null) {
            ticketsDto.setActive(true);
            savedTicket = ticketsRepository.save(ticketsConverter.fromTicketDtoToTicket(ticketsDto));
            flightsService.buyTicket(ticketsDto.getFlight().getId(), ticketsDto.getNumberOfSeats());
            seatsService.saveSeats(savedTicket,
                    ticketsDto.getFlight().getNumberOfFreeSeats(), ticketsDto.getNumberOfSeats());
            return ticketsConverter.fromTicketToTicketDto(savedTicket);
        }
        return ticketsConverter.fromTicketToTicketDto(ticketsRepository.save(ticketsConverter.fromTicketDtoToTicket(ticketsDto)));
    }

    private void validateTicketDto(TicketsDto ticketsDto) throws ValidationException {
        if (isNull(ticketsDto)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(ticketsDto.getAccount())) {
            throw new ValidationException("Ticket account is empty");
        }
        if (ticketsDto.getNumberOfSeats() <= 0) {
            throw new ValidationException("Wrong number of seats");
        }
        if (isNull(ticketsDto.getFlight())) {
            throw new ValidationException("Ticket flight is empty");
        }
    }

    @Override
    public void deactivate(Integer id) {
        TicketsDto ticketsDto = findById(id);
        ticketsDto.setActive(false);
        flightsService.returnTicket(ticketsDto.getFlight().getId(), ticketsDto.getNumberOfSeats());
        ticketsRepository.save(ticketsConverter.fromTicketDtoToTicket(ticketsDto));
    }

    @Override
    public TicketsDto findById(Integer id) {
        Optional<Ticket> ticket = ticketsRepository.findById(id);
        return ticket.map(ticketsConverter::fromTicketToTicketDto).orElse(null);
    }

    @Override
    public List<Ticket> findByAccountEmail(String email) {
        List<Ticket> tickets = ticketsRepository.findAllByAccount_Email(email);
        for (Ticket ticket : tickets) {
            Flight flight = ticket.getFlight();
            flight.setPrice(flightsService.getPrice(flight, ticket.getNumberOfSeats()));
        }
        return tickets;
    }

    public void delete(TicketsDto ticketsDto) {
        ticketsRepository.delete(ticketsConverter.fromTicketDtoToTicket(ticketsDto));
    }
}
