import React from "react";
import { ListGroup } from "react-bootstrap";

export default function ListItem({ title, text }) {
  return (
    <ListGroup.Item
      as="li"
      className="d-flex justify-content-between align-items-start"
    >
      <div>
        <div className="fw-bold">{title}</div>
        {text}
      </div>
    </ListGroup.Item>
  );
}
