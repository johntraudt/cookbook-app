import Home from './pages/Home';
import Categories from './pages/Categories';
import Login from './pages/Login';
import Header from './page-elements/Header';
import Footer from './page-elements/Footer';
import Recipe from './pages/Recipe';


import './App.css'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Switch>
          <Route path="/" exact component={Home}/>
          <Route path="/categories" component={Categories}/>
          <Route path="/login" component={Login}/>
          <Route path="/recipe" component={Recipe}/>
        </Switch>
        <Footer />
      </div>

    </Router>
  );
}

export default App;
