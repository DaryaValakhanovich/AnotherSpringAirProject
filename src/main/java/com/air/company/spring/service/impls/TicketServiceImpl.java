package com.air.company.spring.service.impls;

import com.air.company.spring.dto.TicketsDto;
import com.air.company.spring.entity.Flight;
import com.air.company.spring.entity.Ticket;
import com.air.company.spring.exception.ValidationException;
import com.air.company.spring.repository.TicketsRepository;
import com.air.company.spring.service.search.GenericSpecificationsBuilder;
import com.air.company.spring.service.search.SpecificationFactory;
import com.air.company.spring.dto.mappers.TicketsMapper;
import com.air.company.spring.service.FlightsService;
import com.air.company.spring.service.SeatsService;
import com.air.company.spring.service.TicketsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketsService {

    @Autowired
    private SpecificationFactory<Ticket> ticketSpecificationFactory;
    private final TicketsMapper ticketsConverter;
    private final TicketsRepository ticketsRepository;
    private final FlightsService flightsService;
    private final SeatsService seatsService;

    @Override
    public TicketsDto saveTicket(TicketsDto ticketsDto){
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
    public List<TicketsDto> findBy(TicketsDto ticketsDto) {
        GenericSpecificationsBuilder<Ticket> builder = new GenericSpecificationsBuilder<>();
        if (ticketsDto.getNumberOfSeats() != 0) {
            builder.with(ticketSpecificationFactory.isEqual("numberOfSeats", ticketsDto.getNumberOfSeats()));
        }
        if (Objects.nonNull(ticketsDto.getActive())) {
            builder.with(ticketSpecificationFactory.isEqual("active", ticketsDto.getActive()));
        }
        if (Objects.nonNull(ticketsDto.getAccount())) {
            builder.with(ticketSpecificationFactory.isEqual("account", ticketsDto.getAccount()));
        }
        List<Ticket> tickets = ticketsRepository.findAll(builder.build());

        List<TicketsDto> ticketsDtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            Flight flight = ticket.getFlight();
            flight.setPrice(flightsService.getPrice(flight, ticket.getNumberOfSeats()));
            ticketsDtos.add(ticketsConverter.fromTicketToTicketDto(ticket));
        }
        return ticketsDtos;
    }

    public void delete(TicketsDto ticketsDto) {
        ticketsRepository.delete(ticketsConverter.fromTicketDtoToTicket(ticketsDto));
    }

}
