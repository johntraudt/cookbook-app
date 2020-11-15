import Home from './pages/Home';
import Categories from './pages/Categories';
import Login from './pages/Login';
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
import SignUp from './pages/SignUp';

import AuthContext from './page-elements/AuthContext';

import './App.css'

import jwt_decode from 'jwt-decode';
import React, {useState} from 'react';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';

export default function App() {
  const [user, setUser] = useState(null);

  const login = (token) => {
    const { appUserId, sub: username, authorities } = jwt_decode(token);

    // Split the authorities into an array of roles.
    const roles = authorities.split(',');
  
    const user = {
      appUserId: parseInt(appUserId, 10),
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    };
    console.log(user);

    setUser(user);

    return user;
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
            <Route path="/login" component={Login}/>
            <Route path="/recipe" component={Recipe}/>
            <Route path="/results" component={Results}/>
            <Route path="/user" component={UserProfile}/>
            <Route path="/notfound" component={NotFound}/>
            <Route path="/about" component={AboutUs}/>
            <Route path="/privacy" component={Privacy}/>
            <Route path="/post" component={PostRecipe}/>
            <Route path="/signup" component={SignUp}/>
          </Switch>
          <Footer />
        </div>
      </Router>
    </AuthContext.Provider>
  );
}
