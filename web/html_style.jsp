<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    body {
        background: rgba(225, 161, 84, 0.7);
        display: flex;
        min-height: 100vh;
        justify-content: center;

    }
    table {
        border: 1px solid black;
        border-collapse: collapse;
        width: 100%;
    }
    td, th {
        border: 1px solid black;
        text-align: left;
        padding: 8px;
    }
    tr:nth-child(even) {background-color: #ed9d63;}
    button {
        box-shadow:inset 0px 1px 0px 0px #fff6af;
        background: #ffec64 linear-gradient(to bottom, #ffec64 5%, #ffab23 100%);
        border-radius:6px;
        border:1px solid #ffaa22;
        display:inline-block;
        cursor:pointer;
        color:#333333;
        font-family:Arial;
        font-size:15px;
        font-weight:bold;
        padding:6px 24px;
        text-decoration:none;
        text-shadow:0px 1px 0px #ffee66;
    }
    button:hover {
        background: #ffab23 linear-gradient(to bottom, #ffab23 5%, #ffec64 100%);
    }
    button:active {
        position:relative;
        top:1px;
    }
    #btn_panier {
        color: #fff !important;
        text-transform: uppercase;
        text-decoration: none;
        background: #ed4720;
        padding: 10px;
        border-radius: 5px;
        display: inline-block;
        border: none;
        transition: all 0.4s ease 0s;
    }
    #btn_panier:hover {
        background: #434343;
        letter-spacing: 1px;
        -webkit-box-shadow: 0px 5px 40px -10px rgba(0,0,0,0.57);
        -moz-box-shadow: 0px 5px 40px -10px rgba(0,0,0,0.57);
        box-shadow: 5px 40px -10px rgba(0,0,0,0.57);
        transition: all 0.4s ease 0s;
    }
    #btn_add {

    }

    #btn_add {
        border-radius: 4px;
        background: linear-gradient(to right, #f2e700, #f25600) !important;
        border: none;
        color: #FFFFFF;
        text-align: center;
        text-transform: uppercase;
        font-size: 15px;
        padding: 5px;
        width: 100px;
        transition: all 0.4s;
        cursor: pointer;
        margin: 5px;
    }
    #btn_add span {
        cursor: pointer;
        display: inline-block;
        position: relative;
        transition: 0.4s;
    }
    #btn_add span:after {
        content: '\002b';
        position: absolute;
        opacity: 0;
        top: 0;
        right: -20px;
        transition: 0.5s;
    }
    #btn_add:hover span {
        padding-right: 25px;
    }
    #btn_add:hover span:after {
        opacity: 1;
        right: 0;
    }
</style>
