import React, { useState, useEffect } from "react";
import { Container, Button, Modal, Alert } from "react-bootstrap";
import Cars from "../../components/cars/cars-list";
import CarModal from "../../components/car-modal/car-modal";
import Car from "../../components/car/car";
import styles from "./car-modify.module.css";

export default function CarModify() {
  const [cars, setCars] = useState([]);
  const [isAlertVisible, setIsAlertVisible] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [formMethod, setFormMethod] = useState("POST");
  const [carToUpdate, setCarToUpdate] = useState({});
  const [modalTitle, setModalTitle] = useState("");

  const closeModel = () => {
    setShowModal(false);
  };

  const onSubmit = (submitedCar) => {
    const car = cars.find((car) => car.id === submitedCar.id);
    if (!car) {
      setCars([...cars, submitedCar]);
      closeModel();
      return;
    }

    const newCars = cars.map((car) => {
      if (car.id !== submitedCar.id) return car;
      return submitedCar;
    });
    setCars(newCars);
    closeModel();
  };

  const onCarDeleted = (car) => {
    setCars((cars) => {
      return cars.filter((currentCar) => currentCar.id !== car.id);
    });
  };

  const onCarUpdate = (car) => {
    setFormMethod("PUT");
    setModalTitle("Update car");
    setCarToUpdate(car);
    setShowModal(true);
  };

  const onAddCarClicked = () => {
    setFormMethod("POST");
    setModalTitle("Add car");
    setShowModal(true);
  };

  const carList = cars.map((car) => {
    return (
      <Car
        isModifiable={true}
        key={car.id}
        car={car}
        showAlert={setIsAlertVisible}
        onCarDeleted={onCarDeleted}
        onCarUpdate={onCarUpdate}
      />
    );
  });

  useEffect(() => {
    let timer;
    if (isAlertVisible) {
      timer = setTimeout(() => {
        setIsAlertVisible(false);
      }, 3000);
    }

    return () => {
      if (timer) {
        clearTimeout(timer);
      }
    };
  }, [isAlertVisible]);

  const getCarModal = () => {
    if (formMethod === "POST") {
      return (
        <CarModal
          title={modalTitle}
          onSubmit={onSubmit}
          formMethod={formMethod}
        />
      );
    }

    return (
      <CarModal
        title={modalTitle}
        onSubmit={onSubmit}
        formMethod={formMethod}
        car={carToUpdate}
      />
    );
  };

  return (
    <>
      {isAlertVisible && <Alert variant="danger">Somethin went wrong</Alert>}
      <Container className={styles.container}>
        <Button variant="primary" onClick={onAddCarClicked} className={styles.addCar}>
          Add new car
        </Button>
        <Cars modifiable={true} setCars={setCars} cars={carList} />
      </Container>
      <Modal show={showModal} onHide={closeModel} size="xl">
        {getCarModal()}
      </Modal>
    </>
  );
}
