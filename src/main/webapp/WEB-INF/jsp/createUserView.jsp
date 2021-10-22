<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="_header.jsp"></jsp:include>

<style>
    html {
        font-size: 62.5%;
    }

    body {
        font-size: 1.6rem;
        line-height: 1.6;
        font-family: 'Source Sans Pro';
        background-color: rgb(245, 245, 245);
    }

    h1 {
        font-size: 2.5rem;
        padding: 15px 20px;
        background-color: rgba(255, 219, 58, 0.3);
        margin-bottom: 20px;
        text-align: center;
    }

    a {
        text-decoration: underline;
        color: inherit;
    }

    .container {
        margin: 80px auto;
        width: 90%;
        max-width: 800px;
    }

    .registration {
        background-color: #fff;
        max-width: 600px;
        margin: 0 auto;
        padding-bottom: 20px;
        box-shadow: 1px 1px 5px 0px rgba(0,0,0,0.3);
        border-bottom: 5px solid #ffdb3a;
    }


    .input-requirements {
        font-size: 1.3rem;
        font-style: italic;
        text-align: left;
        list-style: disc;
        list-style-position: inside;
        max-width: 400px;
        margin: 10px auto;
        color: rgb(150,150,150);
    }


    .input-requirements li.invalid {
        color: #e74c3c;
    }
    .input-requirements li.valid {
        color: #2ecc71;
    }

    .input-requirements li.valid:after {
        display: inline-block;
        padding-left: 10px;
        content: "\2713";
    }

    /* FormHack v1.2.0 (formhack.io) */

    /* Config ----------------------------- */
    :root {

        /* Font */
        --fh-font-family: 'Source Sans Pro', sans-serif;
        --fh-font-size: 15px;
        --fh-font-color: rgb(40, 40, 40);

        /* Borders */
        --fh-border-radius: 3px;
        --fh-border-width: 1px;
        --fh-border-style: solid;
        --fh-border-color: rgb(200, 200, 200);

        /* Inputs, Textareas, Select, Option */
        --fh-input-height: 40px;
        --fh-input-width: 100%;
        --fh-input-max-width: 400px;
        --fh-input-bg-color: rgb(250,250,250);
        --fh-focus-bg-color: var(--fh-input-bg-color);
        --fh-focus-border-color: #000;
        --fh-focus-font-color: var(--fh-font-color);

        /* Select Vendor Styling */
        --fh-select-vendor-styling: none; /* comment this out to maintain vendor styling */


        /* Buttons & Input Submits */
        --fh-button-height: 40px;
        --fh-button-width: 100%;
        --fh-button-max-width: 200px;
        --fh-button-font-color: var(--fh-font-color);
        --fh-button-bg-color: rgba(255, 219, 58,0.3);
        --fh-button-hover-bg-color: rgb(255, 219, 58);
        --fh-button-hover-font-color: var(--fh-font-color);

        /* Layout */
        --fh-layout-display: block;
        --fh-layout-margin: 10px auto; /* change to "10px auto" to center */
        --fh-layout-text-align: center;
    }



    /* Global Reset Styles ------------------ */



    input,
    textarea,
    select,
    option,
    optgroup,
    button,
    legend,
    fieldset {
        box-sizing: border-box;
        outline: none;

        font-family: var(--fh-font-family);
        font-size: var(--fh-font-size);
        color: var(--fh-font-color);
        vertical-align: top;

        display: var(--fh-layout-display);
        margin: var(--fh-layout-margin);
        text-align: var(--fh-layout-text-align);
    }


    datalist {
        font-family: var(--fh-font-family);
        font-size: var(--fh-font-size);
    }

    label {
        display: block;
        margin: var(--fh-layout-margin);
        text-align: var(--fh-layout-text-align);
        margin-bottom: 20px;
        position: relative;
        padding: 0 20px;
    }



    /* Input & Textarea ------------------ */

    /* Fields with standard width */
    input[type="text"],
    input[type="email"],
    input[type="password"],
    input[type="search"],
    input[type="color"],
    input[type="date"],
    input[type="datetime-local"],
    input[type="month"],
    input[type="number"],
    input[type="tel"],
    input[type="time"],
    input[type="url"],
    input[type="week"],
    input[list],
    input[type="file"],
    select,
    textarea {
        width: var(--fh-input-width);
        max-width: var(--fh-input-max-width);
        padding: calc( var(--fh-input-height) / 5 );
        background-color: var(--fh-input-bg-color);

        border-radius: var(--fh-border-radius);
        border-width: var(--fh-border-width);
        border-style: var(--fh-border-style);
        border-color: var(--fh-border-color);
    }

    /* Fields with standard height */
    input[type="text"],
    input[type="email"],
    input[type="password"],
    input[type="search"],
    input[type="color"],
    input[type="date"],
    input[type="datetime-local"],
    input[type="month"],
    input[type="number"],
    input[type="tel"],
    input[type="time"],
    input[type="url"],
    input[type="week"],
    input[list] {
        height: var(--fh-input-height);
        -webkit-appearance: none;
    }

    /* Other */

    textarea {
        -webkit-appearance: none;
        overflow: auto;
    }

    input[type="range"] {
        height: var(--fh-input-height);
        width: var(--fh-input-width);
        max-width: var(--fh-input-max-width);
    }

    input[type="file"] {
        min-height: var(--fh-input-height);
    }

    input[type="search"] {
        height: var(--fh-input-height);
        -webkit-appearance: none;
    }
    input[type="search"]::-webkit-search-cancel-button,
    input[type="search"]::-webkit-search-decoration {
        -webkit-appearance: none;
    }

    input[type="checkbox"],
    input[type="radio"] {
        display: inline-block;
        vertical-align: middle;
    }
    /* For checkbox and radio to be centered, need to wrap the input and label in a span -
    /* .checkbox-container {
    /*  display: block;
    /*  text-align: center;
    /* }
    /* Select ------------------ */

    select {
        height: var(--fh-input-height);

        -webkit-appearance: var(--fh-select-vendor-styling, menulist);
        -moz-appearance: var(--fh-select-vendor-styling, menulist);
        -ms-appearance: var(--fh-select-vendor-styling, menulist);
        -o-appearance: var(--fh-select-vendor-styling, menulist);


    }

    select[multiple] {
        height: auto;
        min-height: var(--fh-input-height);
        padding: 0;
    }

    select[multiple] option {
        margin: 0;
        padding: calc( var(--fh-input-height) / 5 );
    }

    /* Fieldset ------------------ */

    fieldset {
        padding: 0;
        border: 0;
    }

    legend {
        padding: 0;
        font-weight: inherit;
    }

    /* Buttons, Input Type Submit/Reset ------------------ */

    button,
    input[type="button"],
    input[type="submit"],
    input[type="reset"],
    input[type="image"] {
        height: var(--fh-button-height);
        width: var(--fh-button-width);
        max-width: var(--fh-button-max-width);
        background-color: var(--fh-button-bg-color);
        padding: calc( var(--fh-input-height) / 5 );
        cursor: pointer;

        color: var(--fh-button-font-color);
        font-weight: 700;

        -webkit-appearance: none;
        -moz-appearance: none;

        border-radius: var(--fh-border-radius);
        border-width: var(--fh-border-width);
        border-style: var(--fh-border-style);
        border-color: var(--fh-border-color);



        box-shadow: 1px 1px 5px 0px rgba(0,0,0,0.2);
    }

    input[type="image"] {
        text-align: center;
        padding: calc( var(--fh-input-height) / 5 );
    }

    /* States ------------------ */

    input[disabled],
    textarea[disabled],
    select[disabled],
    option[disabled],
    button[disabled] {
        cursor: not-allowed;
    }

    input:focus,
    textarea:focus,
    select:focus,
    option:focus,
    button:focus  {
        background-color: var(--fh-focus-bg-color);
        border-color: var(--fh-focus-border-color);
    }

    input[type="checkbox"]:focus,
    input[type="radio"]:focus {
        outline: var(--fh-focus-border-color) solid 2px;
    }

    button:hover,
    input[type="button"]:hover,
    input[type="submit"]:hover,
    input[type="reset"]:hover,
    button:focus,
    input[type="button"]:focus,
    input[type="submit"]:focus,
    input[type="reset"]:focus {
        background-color: var(--fh-button-hover-bg-color);
        color: var(--fh-button-hover-font-color);
    }




    /* Custom ------------------ */


    input:not([type="submit"]):valid {
        border-color: #2ecc71;
    }


    /* Hide and show related .input-requirements when interacting with input */

    input:not([type="submit"]) + .input-requirements {
        overflow: hidden;
        max-height: 0;
        transition: max-height 1s ease-out;
    }

    input:not([type="submit"]):hover + .input-requirements,
    input:not([type="submit"]):focus + .input-requirements,
    input:not([type="submit"]):active + .input-requirements {
        max-height: 1000px; /* any large number (bigger then the .input-requirements list) */
        transition: max-height 1s ease-in;
    }

</style>
<script>
    function function1(){
        document.getElementsByTagName('p')[0].hidden = true;
    }
</script>
<div class="container">
<form:form method="POST" cssClass="registration" modelAttribute="userForm">
        <h1>Registration Form</h1>
        <p style="color: red;" align="center">${errorString} </p>
        <label for="email">
            <span>Email</span>
            <form:input type="text" path="email" placeholder="Email"
                        autofocus="true" onkeydown="function1()"></form:input>
            <form:errors path="email"></form:errors>
            <ul class="input-requirements">
                <li>At least 3 characters long</li>
            </ul>
        </label>


        <label for="number">
            <span>Phone number</span>
            <form:input onkeydown="function1()" type="text" path="number" placeholder="Number"></form:input>
            <ul class="input-requirements">
                <li>acceptable format:</li>
                <li>123-456-7899</li>
                <li>1234567899</li>
            </ul>
        </label>

        <label for="password">
            <span>Password</span>
            <form:input onkeydown="function1()" type="password" path="password" placeholder="Password" maxlength="100" minlength="8"></form:input>
            <ul class="input-requirements">
                <li>At least 8 characters long (and less than 100 characters)</li>
                <li>Contains at least 1 number</li>
                <li>Contains at least 1 lowercase letter</li>
                <li>Contains at least 1 uppercase letter</li>
                <li>Contains a special character (e.g. @ !)</li>
            </ul>
        </label>

        <label for="password_repeat">
            <span>Repeat Password</span>
            <form:input  id="password_repeat" onkeydown="function1()" type="password" path="passwordConfirm"
                        placeholder="Confirm your password" maxlength="100" minlength="8"></form:input>
            <form:errors path="password"></form:errors>
        </label>

        <br>

        <input type="submit">

    </form>
</form:form>
</div>
<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>