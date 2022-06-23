import React, {useState, useEffect} from 'react'
import {Table, Container, Row, Col, Form, FormGroup, FormControl, FormLabel, FormCheck} from 'react-bootstrap'
import {useUserContext} from "../../context/user-context";


export default function Report() {
  const [cars, setCars] = useState([]);
  const [filteredCars, setFilteredCars] = useState([]);
  const [payments, setPayments] = useState([]);
  const [filterOptions, setFilterOptions] = useState({
        searchCarName: "",
        selectedRadioButton: "radioAll"
      })
  const {token, remove, removeUserRole } = useUserContext();

  useEffect(() => {
    const fetchCars = async () => {
      const response = await fetch("http://localhost:8080/api/cars");
      const data = await response.json()
      setCars(data)
      setFilteredCars(data)
      console.log(data)
    };

    fetchCars()
  }, [setCars])

  useEffect(() => {
    const fetchPayments = async () => {
      const response = await fetch("http://localhost:8080/api/payments", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      if (response.status === 401) {
        remove()
        removeUserRole()
      }
      const data = await response.json()
      setPayments(data)
    };

    fetchPayments()
  }, [setPayments])

  const isRadioSelected = (value) => {
    return value === filterOptions.selectedRadioButton;
  }

  const handleRadioSelect = (e) => {
    setFilterOptions({...filterOptions, selectedRadioButton:e.target.value})
    filterCarsWhenRadio(e.target.value)
  }

  const filterCarsByName = (filtered, searchedName) => {
    return !!searchedName ? filtered.filter((car) => car.name.includes(searchedName)) : cars
  }

  const handleCarSearch = (e) => {
    setFilterOptions({...filterOptions, searchCarName:e.target.value})
    filterCarsWhenName(e.target.value)
  }

  const filterCarsByAvailability = (filtered, selected) => {
    if (selected === "radioAll") return filtered;
    else if (selected === "radioAvailable") return filtered.filter((car) => car.sold === false)
    else return filtered.filter((car) => car.sold === true)
  }

  const filterCarsWhenName = (carNameSearch) => {
    const filteredName = filterCarsByName(cars, carNameSearch)
    const filteredAvailability = filterCarsByAvailability(filteredName, filterOptions.selectedRadioButton)
    setFilteredCars(filteredAvailability)
  }

  const filterCarsWhenRadio = (selectedRadioButton) => {
    const filteredName = filterCarsByName(cars, filterOptions.searchCarName)
    const filteredAvailability = filterCarsByAvailability(filteredName, selectedRadioButton)
    setFilteredCars(filteredAvailability)
  }

  return (
    <Row>
      <Col>
        <Container>
          <h2 className="text-center mb-3 mt-2">Cars report</h2>
          <Form>
            <FormGroup className="mb-2">
              <FormLabel className="me-3">Availability:</FormLabel>
              <FormCheck name="availability" value="radioAll" label="All" type="radio" checked={isRadioSelected("radioAll")} onChange={handleRadioSelect} inline />
              <FormCheck name="availability" value="radioSold" label="Sold" type="radio" checked={isRadioSelected("radioSold")} onChange={handleRadioSelect} inline />
              <FormCheck name="availability" value="radioAvailable" label="Available" type="radio" checked={isRadioSelected("radioAvailable")} onChange={handleRadioSelect} inline />
            </FormGroup>
            <FormGroup >
              <FormControl name="search" placeholder="Search by name" value={filterOptions.searchCarName} onChange={handleCarSearch} type="text" className="mb-2" />
            </FormGroup>
          </Form>
          <Table striped hover condensed="true">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Year</th>
                <th>Color</th>
                <th>Price</th>
                <th>Is Sold</th>
                <th>Fuel type</th>
              </tr>
            </thead>
            <tbody>
            {
                filteredCars && filteredCars.map((car) => (
                    <tr key={car.id}>
                      <td>{car.id}</td>
                      <td>{car.name}</td>
                      <td>{car.year}</td>
                      <td>{car.exteriorColor}</td>
                      <td>{car.price}</td>
                      <td>{car.sold.toString()}</td>
                      <td>{car.fuelType}</td>
                    </tr>
                ))
            }
            </tbody>
          </Table>
        </Container>
      </Col>
      <Col>
        <Container>
          <h2 className="text-center mb-3 mt-2">All payments report</h2>
          <Table striped hover condensed="true">
            <thead>
            <tr>
              <th>ID</th>
              <th>User</th>
              <th>Car</th>
              <th>Amount</th>
              <th>Date of purchase</th>
            </tr>
            </thead>
            <tbody>
            {
                payments && payments.map((payment) => (
                    <tr key={payment.id}>
                      <td>{payment.id}</td>
                      <td>{payment.userId}</td>
                      <td>{payment.carId}</td>
                      <td>{payment.amount}</td>
                      <td>{payment.date}</td>
                    </tr>
                ))
            }
            </tbody>
          </Table>
        </Container>
      </Col>
    </Row>
  )
}
