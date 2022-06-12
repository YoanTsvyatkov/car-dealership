import React from "react";
import { Card, Button, Col } from "react-bootstrap";

export default function Car({ car, onSeeDetailsClicked }) {
  return (
    <Col>
      <Card style={{ width: "34rem" }}>
        <Card.Img variant="top" src={car.photoUrl} />
        <Card.Body>
          <Card.Title>{car.name}</Card.Title>
          <Card.Text>{car.carDescription}</Card.Text>
          <Button variant="primary" onClick={() => onSeeDetailsClicked(car.id)}>See details</Button>
        </Card.Body>
      </Card>
    </Col>
  );
}
