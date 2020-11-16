import React from 'react';
import Form from 'react-bootstrap/Form';
import Errors from './Errors';

class SignUp extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: [],
      userId: 0,
      userName: "",
      email: "",
      passwordHash: "",
      firstName: "",
      lastName: "",
      role: {
        userRoleId: 1,
        name: "USER",
      },
      userRoleId: 1,
      active: true,
    };

  }

  handleSubmit = (event) => {
    event.preventDefault();

    const {
      userName,
      email,
      passwordHash,
      firstName,
      lastName,
      role,
      userRoleId,
      active
    } = this.state;

    fetch('http://localhost:8080/api/user', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        userName,
        email,
        passwordHash,
        firstName,
        lastName,
        role,
        userRoleId,
        active,
      })
    })
    .then((response) => {
      if (response.status === 201) {
        response.json().then(data => console.log(data));
        window.location.href = 'http://localhost:3000/';
      } else if (response.status === 400) {
        response.json().then(data => {
          console.log(data);
          this.setState({
            errors: data
          });
      });
      } else {
          console.log("Something went wrong.");
          throw new Error(`Unexpected response: ${response}`);
      }
  })
  }
  
  handleFirstName = (event) => {
    this.setState ({
      firstName: event.target.value
    });
  }

  handleLastName = (event) => {
    this.setState ({
      lastName: event.target.value
    });
  }

  handleEmail = (event) => {
    this.setState ({
      email: event.target.value
    });
  }

  handleUsername = (event) => {
    this.setState ({
      userName: event.target.value
    });
  }

  handlePassword = (event) => {
    this.setState ({
      passwordHash: event.target.value
    });
  }


  render() {
    const {
      errors
    } = this.state;

    return (
      <div  className="container full-body mt-4">
        <Errors errors={errors} />

        <Form onSubmit={this.handleSubmit}>
          <Form.Row>
            <Form.Group controlId="formBasicEmail">
              <Form.Control value={this.state.firstName} onChange={this.handleFirstName} type="text" placeholder="First name" />
            </Form.Group>
            <Form.Group controlId="formBasicEmail">
              <Form.Control value={this.state.lastName} onChange={this.handleLastName} type="text" placeholder="Last name" />
            </Form.Group>
          </Form.Row>

          <Form.Row>
            <Form.Group controlId="formHoriztonalEmail">
              <Form.Control value={this.state.email} onChange={this.handleEmail} type="email" placeholder="Email" />
            </Form.Group>
          </Form.Row>

          <Form.Row>
            <Form.Group controlId="formBasicEmail">
              <Form.Control value={this.state.userName} onChange={this.handleUsername} type="text" placeholder="Username" />
            </Form.Group>
            <Form.Group controlId="formGridPassword">
              <Form.Control value={this.state.passwordHash} onChange={this.handlePassword} type="password" placeholder="Password" />
            </Form.Group>
          </Form.Row>

          {/* <label>Email</label>
          <input value={this.state.email} type="text" placeholder="Email" onChange={this.handleEmail}/>

          <label>Username</label>
          <input value={this.state.userName} type="text" placeholder="Username" onChange={this.handleUsername}/>

          <label>Password</label>
          <input value={this.state.passwordHash} type="password" placeholder="Password" onChange={this.handlePassword}/> */}

          <button type="submit" className="btn btn-primary">Create</button>
        </Form>
      </div>
    );
  }
    
}

export default SignUp;