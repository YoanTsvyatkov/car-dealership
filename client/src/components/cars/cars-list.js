import React, { useEffect } from "react";
import styles from "./cars-list.module.css";

export default function Cars({ setCars, cars }) {
  useEffect(() => {
    const fetchCars = async () => {
      const response = await fetch("http://localhost:8080/api/cars");
      const data = await response.json();
      const availableCars = data.filter((car) => car.sold === false);
      setCars(availableCars);
    };

    fetchCars();
  }, [setCars]);

  return (
    <>
      <div className={styles.container}>{cars}</div>
    </>
  );
}
