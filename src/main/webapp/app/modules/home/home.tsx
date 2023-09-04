import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import Login from '../login/login';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className="px-4 py-5 my-5 text-center">
      <h1 className="display-5 fw-bold">Prueba Técnica Epico</h1>
      <div className="col-lg-6 mx-auto">
        <p className="lead mb-4">Esta pagina crea, lee, edita y elimina datos para un proyecto.</p>
        <div className="d-grid gap-2 d-sm-flex justify-content-sm-center">
          
          <a className="btn btn-outline-secondary btn-lg px-4" href="http://localhost:8080/epicoitem/new" role="button">Registro</a>
          <a className="btn btn-outline-secondary btn-lg px-4" href="http://localhost:8080/login" role="button">Inicio Sesión</a>
        </div>
      </div>
    </div>
  );
};

export default Home;
