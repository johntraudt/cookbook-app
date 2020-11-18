import React from 'react';
import Form from 'react-bootstrap/Form';
import Errors from './Errors';
// import Loading from '../resources/loading-2.gif';
import Success from '../page-elements/Success'

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
      isSuccess: false,
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
      userRoleId,
    } = this.state;

    fetch(`${process.env.REACT_APP_URL}/api/user`, {
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
        userRoleId,
      })
    })
    .then((response) => {
      if (response.status === 201) {
        response.json().then(data => console.log(data));
        this.setState({
          errors:[],
          userName: "",
          email: "",
          passwordHash: "",
          firstName: "",
          lastName: "",
          isSuccess: true,
        })
        setTimeout(()=>{window.location.href = '/'}, 2500);
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
      errors,
      isSuccess
    } = this.state;

    return (
      <div className="container full-body mt-4 d-flex flex-wrap justify-content-center">
          <div className="mr-3">
            <Errors errors={errors} />
          </div>
        
        <div className="flex">
          <div className="align-self-center">
            <h3>Create Account</h3>
          </div>
          { !isSuccess && (
          <Form  onSubmit={this.handleSubmit} >
            <Form.Row>
              <Form.Group className="mr-2" controlId="validationCustom02">
                <Form.Control value={this.state.firstName} onChange={this.handleFirstName} type="text" placeholder="First name" />
              </Form.Group>
              <Form.Group controlId="">
                <Form.Control value={this.state.lastName} onChange={this.handleLastName} type="text" placeholder="Last name" />
                <p></p>
              </Form.Group>
            </Form.Row>

            <Form.Row>
              <Form.Group controlId="formBasicEmail">
                <Form.Control value={this.state.email} onChange={this.handleEmail} type="email" placeholder="Email" />
                <Form.Text className="text-muted">
                  We'll never share your email with anyone else. Promise.
                </Form.Text>
              </Form.Group>
            </Form.Row>

            <Form.Row>
              <Form.Group className="mr-2" controlId="">
                <Form.Control value={this.state.userName} onChange={this.handleUsername} type="text" placeholder="Username" />
              </Form.Group>
              <Form.Group controlId="formGridPassword">
                <Form.Control value={this.state.passwordHash} onChange={this.handlePassword} type="password" placeholder="Password" />
              </Form.Group>
            </Form.Row>

            <button type="submit" className="float-right btn btn-primary btn-create">Create</button>
          </Form>
          )}
          {isSuccess && (
            <Success message='Success! Redirecting....'/>
          )}
        </div>
      </div>
    );
  }
}

export default SignUp;