import Home from './pages/Home';
import Categories from './pages/Categories';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import Header from './page-elements/Header';
import Footer from './page-elements/Footer';
import Recipe from './pages/Recipe';
import Results from './pages/Results';
import UserProfile from './pages/UserProfile';
import NotFound from './pages/NotFound';
import AboutUs from './pages/AboutUs';
import ScrollToTop from './ScrollToTop';
import Privacy from './pages/PrivacyPolicy';
import PostRecipe from './pages/PostRecipe';
import Cookbook from './pages/Cookbook';
import Tag from "./pages/Tag";

import AuthContext from './page-elements/AuthContext';

import './App.css'

import jwt_decode from 'jwt-decode';
import React, {useState} from 'react';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';

export default function App() {
  const [user, setUser] = useState(null);

  const login = (token) => {
    const { sub: userName, authorities: role} = jwt_decode(token);  

    console.log('here')
    console.log(userName)

    const user = {
      userName,
      // firstName,
      // lastName,
      role,
      // userRoleId,
      // active,
      token,
    };
    console.log(user);



    // // console.log(user);

    // // return user;
    //   }
    // };
    setUser(user);

    return user;
  }

  const findUserByUserName = () => {
    fetch(`http://localhost:8080/api/user/name/${user.userName}`) 
    .then(response => response.json())
    .then((data) => {
      setUser({
        userName: user.userName,
        firstName: data.firstName,
        lastName: data.lastName,
        email: data.email,
        role: user.role,
        token: user.token,
      });
    });
  };

  const logout = () => {
    setUser(null);
  };

  const auth = {
    user,
    login,
    logout
  };

  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <ScrollToTop />
        <div className="App">
          <Header />
          <Switch>
            <Route path="/" exact component={Home}/>
            <Route path="/categories" exact component={Categories}/>
            <Route path="/login" component={user ? Home : Login}/>
            <Route path="/signup" component={SignUp}/>
            <Route path="/recipe" component={Recipe}/>
            <Route path="/results" component={Results}/>
            <Route path="/user" component={user ? UserProfile : Login}/>
            <Route path="/notfound" component={NotFound}/>
            <Route path="/about" component={AboutUs}/>
            <Route path="/privacy" component={Privacy}/>
            <Route path="/post" component={user ? Login : PostRecipe}/>
            <Route path="/cookbook" component={Cookbook}/>
            <Route path="/recipe-tag" component={Tag}/>
          </Switch>
          <Footer />
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

