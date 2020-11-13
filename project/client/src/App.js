import Home from './pages/Home';
import Categories from './pages/Categories';
import Login from './pages/Login';
import Header from './page-elements/Header';
import Footer from './page-elements/Footer';
import Recipe from './pages/Recipe';
import Results from './pages/Results';
import UserProfile from './pages/UserProfile';

import './App.css'

import React, {useState, useEffect} from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    setUser()}, []
  )

  // const user = {
  //   userId=0,
  //   userName='testUser (replace me!!)',
  //   role='user',
  // };

  const logout = () => {
    setUser(null);
  }


  return (
    <Router>
      <div className="App">
        <Header />
        <Switch>
          <Route path="/" exact component={Home}/>
          <Route path="/categories" exact component={Categories}/>
          <Route path="/login" component={Login}/>
          <Route path="/recipe" component={Recipe}/>
          <Route path="/results" component={Results}/>
          <Route path="/user" component={UserProfile}/>
        </Switch>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
