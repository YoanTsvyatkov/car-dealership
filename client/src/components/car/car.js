import React from "react";
import { Card, Button, Col, ButtonGroup } from "react-bootstrap";
import { Link } from "react-router-dom";
import { useUserContext } from "../../context/user-context";
import styles from "./car.module.css";

export default function Car({
  car,
  isModifiable,
  showAlert,
  onCarDeleted,
  onCarUpdate,
}) {
  const { token } = useUserContext();
  const { remove, removeUserRole } = useUserContext();

  const onDeleteCarClicked = async () => {
    console.log(`token: ${token}`);
    const response = await fetch(`http://localhost:8080/api/cars/${car.id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (response.status === 401) {
      remove()
      removeUserRole()
      return;
    }

    await response.json();

    if (!response.ok) {
      showAlert(true);
    } else {
      onCarDeleted(car);
    }
  };

  let carButtons;
  if (isModifiable) {
    carButtons = (
      <ButtonGroup>
        <Button variant="danger" onClick={() => onDeleteCarClicked()}>
          Delete
        </Button>
        <Button variant="light" onClick={() => onCarUpdate(car)}>
          Edit
        </Button>
      </ButtonGroup>
    );
  } else {
    carButtons = (
      <Link to={`/cars/${car.id}`}>
        <Button variant="primary">See details</Button>
      </Link>
    );
  }
  return (
    <Card className={styles.card}>
      <Card.Img variant="top" src={car.photoUrl} style={{ height: "55%" }} />
      <Card.Body style={{ height: "45%" }}>
        <Card.Title>{car.name}</Card.Title>
        <Card.Text className={styles.description}>
          {car.carDescription}
        </Card.Text>
        {carButtons}
      </Card.Body>
    </Card>
  );
}
