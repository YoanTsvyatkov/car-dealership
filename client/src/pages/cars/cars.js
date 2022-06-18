import React, { useState } from "react";
import { Container } from "react-bootstrap";
import styles from "./cars.module.css";
import Cars from "../../components/cars/cars-list";
import Car from "../../components/car/car";

export default function CarsPage() {
  const [cars, setCars] = useState([]);
  const carList = cars.map((car) => {
    return <Car isModifiable={false} key={car.id} car={car} />;
  });

  return (
    <>
      <Container className="mt-3">
        <h2 className={styles.title}>All cars</h2>
        <Cars modifiable={false} setCars={setCars} cars={carList} />
      </Container>
    </>
  );
}
