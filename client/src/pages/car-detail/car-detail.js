import React, { useEffect, useState } from "react";
import {
  Container,
  Image,
  Row,
  Col,
  ListGroup,
  Button,
  Modal,
} from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import styles from "./car-detail.module.css";
import ListItem from "../../components/list-item/list-item";
import { useUserContext } from "../../context/user-context";

export default function CarDetails() {
  const [car, setCar] = useState({});
  const [showModal, setShowModal] = useState(false);
  const params = useParams();
  const { token } = useUserContext();
  const navigate = useNavigate();
  const { remove, removeUserRole } = useUserContext();

  useEffect(() => {
    const getCar = async () => {
      const response = await fetch(
        `http://localhost:8080/api/cars/${params.carId}`
      );
      const data = await response.json();
      setCar(data);
    };

    getCar();
  }, [params.carId]);

  const onOpenDialogClick = () => setShowModal(true);
  const handleClose = () => setShowModal(false);
  const handleBuy = async () => {
    const body = {
      amount: car.price,
      carId: car.id,
    };
    const response = await fetch("http://localhost:8080/api/payments", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(body),
    });

    if (response.status === 401) {
      remove();
      removeUserRole();
    }

    await response.json();

    handleClose();

    if (!response.ok) {
      alert("Something went wrong");
      return;
    }
    navigate("/cars");
  };

  return (
    <Container className={styles.container}>
      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Are you sure you want to buy {car.name}?</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {car.name} will cost you: {car.price}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={handleBuy}>
            Confirm buy
          </Button>
        </Modal.Footer>
      </Modal>

      <Row className={styles.mainRow}>
        <Col>
          <Image src={car.photoUrl} width="100%" height="100%" />
        </Col>
        <Col>
          <h1>{car.name}</h1>
          <div>{car.year}</div>
          <h2 className="mt-4">€ {car.price}</h2>

          <div>{car.carDescription}</div>
          {token && (
            <Button
              variant="success"
              className={styles.buy}
              onClick={onOpenDialogClick}
            >
              Buy
            </Button>
          )}
        </Col>
      </Row>
      <Row className="mt-3">
        <h2>Additional details</h2>
        <div className={styles.additionalDetails}>
          <Row>
            <Col>
              <ListGroup as="ul" className={styles.listGroup}>
                <ListItem title="Fuel type" text={car.fuelType} />
                <ListItem title="Transmission" text={car.transmission} />
                <ListItem title="Milleage" text={car.millage} />
              </ListGroup>
            </Col>
            <Col>
              <ListGroup as="ul" className={styles.listGroup}>
                <ListItem title="Exterior color" text={car.exteriorColor} />
                <ListItem title="Interior color" text={car.interiorColor} />
                <ListItem title="Mpg" text={car.mpg} />
              </ListGroup>
            </Col>
          </Row>
        </div>
      </Row>
    </Container>
  );
}
