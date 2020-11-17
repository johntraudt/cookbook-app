import React, {useState, useEffect, useContext} from 'react';
import { Link, useHistory } from 'react-router-dom';
import Accordion from 'react-bootstrap/Accordion'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import Collapse from 'react-bootstrap/Collapse'
import AuthContext from '../page-elements/AuthContext';
import Rating from '../page-elements/Rating';

export default function UserProfile() {
    const [open, setOpen] = useState(false);
    const [editUser, setEditUser] = useState(false);
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [cookBook, setCookBook] = useState('');
    const [userName, setUserName] = useState('');
    // const [user, setUser] = useState({});
    const [cookBooks, setCookBooks] = useState([]);
    const [recipes, setRecipes] = useState([]);

    const auth = useContext(AuthContext);
    const history = useHistory();

    useEffect(()=>{
        const getCookBooks = () => {
            fetch(`http://localhost:8080/api/cookbook/user/${auth.user.userId}/all`) 
                .then(response => response.json())
                .then((data) => {
                    setCookBooks(data);
                    console.log('below')
                    console.log(data)
                })
        };
        // const getMeasurementUnits = () => {
        //     fetch('http://localhost:8080/api/measurement-unit')
        //         .then(response => response.json())
        //         .then((data) => {
        //             setMeasurementUnits(data);
        //             console.log(data)
        //         })
        // };
        getCookBooks();
    },[]);

    const removeFromCookBook = (book, recipe) => {
        console.log('book:')
        console.log(book)
        console.log('recipe:')
        console.log(recipe)
        fetch(`http://localhost:8080/api/cookbook/${book.cookbookId}/${recipe.recipeId}`, {
            method: 'delete'})
            
            .then((response) => {
                if (response.status >= 400) {
                    history.push("/notfound");
                } 
            });
    }

    const deleteCookBook = (cookbookId) => {

    }

    useEffect(() => {
        setEmail();
        setFirstName();
        setLastName();
        setCookBook();
        setUserName();
    },[console.log(email),
        console.log(firstName),
        console.log(lastName),
        console.log(cookBook),
        console.log(userName)]);    



    return (
        <div className="container full-body">
            <h1 className="text-center m-4">UserName Profile</h1>
            <div className="row mt-5">
                <div className='col-lg-4 col-md-12 col-sm-12'>
                    
                    <table className="table">
                        <thead>
                            <th colspan="2" className="text-center">
                                <h3>User Information</h3>
                            </th>
                        </thead>
                        <tr>
                            <th>UserName</th>
                            <td>
                                {editUser===false ? `${auth.user.userName}` : 
                                    <input type="text" placeholder="New UserName Here..." onChange={event => setUserName(event.target.value)}></input>
                                }
                            </td>
                        </tr>
                        <tr>
                            <th>Email</th>
                            <td>
                                {editUser===false ? `${auth.user.email}` : 
                                    <input type="text" placeholder="New Email Here..." onChange={event => setEmail(event.target.value)}></input>
                                }
                            </td>
                        </tr>
                        <tr>
                            <th>First Name</th>
                            <td>
                                {editUser===false ? `${auth.user.firstName}` : 
                                    <input type="text" placeholder="New First Name Here..." onChange={event => setFirstName(event.target.value)}></input>
                                }
                            </td>
                        </tr>
                        <tr>
                            <th>Last Name</th>
                            <td>
                                {editUser===false ? `${auth.user.lastName}` : 
                                    <input type="text" placeholder="New Last Name Here..." onChange={event => setLastName(event.target.value)}></input>
                                }
                            </td>
                        </tr>
                        <tr>
                            <th>Status</th>
                            <td>{!auth.user.status ? 'active': 'deactivated'}</td>
                        </tr>
                        <tr>
                            <td colspan={editUser === true ? 1 : 2} className="text-center">
                                <button className="btn btn-secondary" onClick={() => editUser===false ? setEditUser(true): setEditUser(false)}>{editUser===false ? 'Edit': 'Cancel'}</button>
                            </td>
                            {editUser && (
                                <td>
                                    <button className="btn btn-secondary" type='submit'>Submit</button>
                                </td>

                            )}
                        </tr>
                    </table>
                </div>

                <div className='col-lg-8 col-md-12 col-sm-12'>

                <Accordion defaultActiveKey="0">
                    <h3 className="m-2">Your Cookbooks:</h3>
                    {
                        cookBooks.map((book) => (
                            <Card>
                                <Card.Header>
                                <Accordion.Toggle as={Button} variant="link" eventKey="0">
                                    {book.title}
                                </Accordion.Toggle>
                                </Card.Header>
                                <Accordion.Collapse eventKey="0">
                                <Card.Body>
                                    {
                                        

                                        book.recipes.map((recipe) => (
                                            <div className="row mb-4">
                                                <div className="col-3">
                                                    <Link className="dark" to={`/recipe/${recipe.recipeId}`}>
                                                        <img src={recipe.imageLink} height='75'></img>
                                                    </Link>
                                                </div>
                                                <div className="col-5">
                                                    <Link className="dark" to={`/recipe/${recipe.recipeId}`}>
                                                        <div>{recipe.name}</div>
                                                        <div>{<Rating detailed={false} reviews={recipe.reviews} />}</div>
                                                        
                                                    </Link>
                                                </div>
                                                <div className="col-4">
                                                <button className="btn btn-danger" onClick={() => removeFromCookBook(book, recipe)}>X</button>
                                                </div>
                                            </div>
                                        ))
                                    }
                                    <div className="d-flex flex-wrap justify-content-center">
                                        <Link className="ml-auto mr-auto" to={`/cookbook/${book.cookbookId}`}>
                                            <button type="button" className="btn btn-info" >View Detailed</button>
                                        </Link>
                                        <button type="button" onClick={(book) => deleteCookBook(book.cookbookId)} className="btn btn-danger ml-auto mr-auto">Delete</button>
                                    </div>
                                </Card.Body>
                                </Accordion.Collapse>
                            </Card>
                        ))
                    }

                </Accordion>
                <Button className="btn-secondary m-2" onClick={() => setOpen(!open)} aria-controls="collapse-form" aria-expanded={open}>Create Cookbook</Button>
                <Collapse in={open}>
                    <div className="m-2" id="collapse-form">
                        <form>
                            <input className="expand" type="text" onChange={event => setCookBook(event.target.value)}></input>
                            <button className="btn btn-secondary ml-2" type="submit">Add Now!</button>
                        </form>
                    </div>
                </Collapse>



                <Accordion defaultActiveKey="0">
                    <h3 className="m-2">Your Recipes:</h3>
                    <Card>
                        <Card.Header>
                        <Accordion.Toggle as={Button} variant="link" eventKey="0">
                            Recipe 1's name
                        </Accordion.Toggle>
                        </Card.Header>
                        <Accordion.Collapse eventKey="0">
                        <Card.Body>
                            <div>Recipe 1's data (picture, directions, tags, ingredients) and a link to the recipe page</div>
                            <div className="d-flex flex-wrap justify-content-center">
                                <a className="btn btn-info ml-auto">View</a>
                                <a className="btn btn-secondary ml-auto mr-auto">Edit</a>
                                <a className="btn btn-danger mr-auto">Delete</a>
                            </div>
                        </Card.Body>
                        </Accordion.Collapse>
                    </Card>
                    <Card>
                        <Card.Header>
                        <Accordion.Toggle as={Button} variant="link" eventKey="1">
                            Recipe 2's name
                        </Accordion.Toggle>
                        </Card.Header>
                        <Accordion.Collapse eventKey="1">
                        <Card.Body>
                            <div>Recipe 2's data (picture, directions, tags, ingredients) and a link to the recipe page</div>
                            <div className="d-flex flex-wrap justify-content-center">
                                <a className="btn btn-info  ml-auto">View</a>
                                <a className="btn btn-secondary ml-auto mr-auto">Edit</a>
                                <a className="btn btn-danger  mr-auto">Delete</a>
                            </div>
                        </Card.Body>
                        </Accordion.Collapse>
                    </Card>
                    <Card>
                        <Card.Header>
                        <Accordion.Toggle as={Button} variant="link" eventKey="2">
                            Recipe 3's name
                        </Accordion.Toggle>
                        </Card.Header>
                        <Accordion.Collapse eventKey="2">
                        <Card.Body>
                            <div>Recipe 3's data (picture, directions, tags, ingredients) and a link to the recipe page</div>
                            <div className="d-flex flex-wrap justify-content-center">
                                <a className="btn btn-info  ml-auto">View</a>
                                <a className="btn btn-secondary ml-auto mr-auto">Edit</a>
                                <a className="btn btn-danger  mr-auto">Delete</a>
                            </div>
                        </Card.Body>
                        </Accordion.Collapse>
                    </Card>
                    
                </Accordion>
                </div>
            </div>
        </div>    
    );
}