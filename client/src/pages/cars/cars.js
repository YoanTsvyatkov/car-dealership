import React, { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import Car from "../../components/car/car";
import { Row } from "react-bootstrap";
import styles from "./cars.module.css";
import { useNavigate } from "react-router-dom";


export default function Cars() {
  const [cars, setCars] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCars = async () => {
      const response = await fetch("http://localhost:8080/api/cars");
      const data = await response.json();
      setCars(data);
    };

    fetchCars();
  }, []);

  const seeDetailsClicked = (carId) => {
    const url = window.location.href
    if (url.includes('cars')) {
      navigate(`${carId}`)
      return
    }

    navigate(`cars/${carId}`)
  }

  const carList = cars.map((car) => {
    return <Car key={car.id} car={car} onSeeDetailsClicked={seeDetailsClicked}/>;
  });

  return (
    <Container className="mt-3">
      <h2 className={styles.title}>All cars</h2>
      <div className={styles.row}>
        <Row xs={1} md={2} className="g-4">
          {carList}
        </Row>
      </div>
    </Container>
  );
}
