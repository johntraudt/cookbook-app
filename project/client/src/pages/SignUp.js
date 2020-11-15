import React from 'react';
import Errors from 'Errors';

class SignUp extends React.Component {
  constructor() {
    super();
    this.state = {
      userId: 0,
      userName: "",
      email: "",
      passwordHash: "",
      firstName: "",
      lastName: "",
    };
  }

  handleSubmit = (event) => {
    event.preventDefault();

    const {
      userName,
      email,
      passwordHash,
      firstName,
      lastName
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
        lastName
      })
    })
    .then((response) => {
      if (response.status === 201) {
        response.json().then(data => console.log(data));
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
      <>
      <Errors errors={errors}/>

      <form onSubmit={this.handleSubmit}>
        <label>First Name</label>
        <input value={this.state.firstName} type="text" placeholder="First Name" onChange={this.handleFirstName}/>
        
        <label>Last Name</label>
        <input value={this.state.lastName} type="text" placeholder="Last Name" onChange={this.handleLastName}/>

        <label>Email</label>
        <input value={this.state.email} type="text" placeholder="Email" onChange={this.handleEmail}/>

        <label>Username</label>
        <input value={this.state.userName} type="text" placeholder="Username" onChange={this.handleUsername}/>

        <label>Password</label>
        <input value={this.state.passwordHash} type="password" placeholder="Password" onChange={this.handlePassword}/>

        <button type="submit" className="btn btn-primary">Create</button>
      </form>
      </>
    );
  }
    
}

export default SignUp;