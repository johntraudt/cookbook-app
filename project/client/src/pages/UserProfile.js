import React, {useState, useEffect, useContext} from 'react';
import { Link, useHistory } from 'react-router-dom';
import Accordion from 'react-bootstrap/Accordion'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import Collapse from 'react-bootstrap/Collapse'
import AuthContext from '../page-elements/AuthContext';
import Rating from '../page-elements/Rating';
import Errors from './Errors';

export default function UserProfile() {
    const [open, setOpen] = useState(false);
    const [editUser, setEditUser] = useState(false);
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [cookBook, setCookBook] = useState('');
    const [userName, setUserName] = useState('');
    // const [password, setPassword] = useState('');
    const [user, setUser] = useState({});
    const [cookBooks, setCookBooks] = useState([]);
    const [recipes, setRecipes] = useState([]);
    const [isActive, setIsActive] = useState(true);
    const [errors, setErrors] = useState([]);

    const auth = useContext(AuthContext);
    const history = useHistory();

    const getCookBooks = () => {
        fetch(`http://localhost:8080/api/cookbook/user/${auth.user.userId}/all`) 
            .then(response => response.json())
            .then((data) => {
                setCookBooks(data);
            });
    };

    const getRecipes = () => {
        fetch(`http://localhost:8080/api/recipe/user/${auth.user.userId}`) 
            .then(response => response.json())
            .then((data) => {
                setRecipes(data);
            });
    };

    const getUser = () => {
        fetch(`http://localhost:8080/api/user/${auth.user.userId}`)
            .then(response => response.json())
            .then((data) => {
                setUser(data);
            });
    };

    useEffect(()=>{
        getCookBooks();
        getRecipes();
        getUser();
    },[]);

    const removeFromCookBook = (book, recipe) => {
        fetch(`http://localhost:8080/api/cookbook/${book.cookbookId}/${recipe.recipeId}`, {
            method: 'delete'})
            .then((response) => {
                if (response.status >= 400) {
                    history.push("/notfound");
                } else {
                    getCookBooks();
                }
            }
        );
    };

    const deleteCookBook = (book) => {
        if (window.confirm("You are about to delete a cookbook.  Are you sure?")) {
        fetch(`http://localhost:8080/api/cookbook/${book.cookbookId}`, {
            method: 'delete'})
            .then((response) => {
                if (response.status >= 400) {
                    history.push("/notfound");
                } else {
                    getCookBooks();
                }
            }
        )};
    };

    const createCookBook = (event) => {
        event.preventDefault();
        fetch(`http://localhost:8080/api/cookbook`, {
            method: 'POST',
            headers: {'Content-Type':'application/json'},
            body: JSON.stringify({
                cookbookId: 0,
                title: cookBook,
                "userId": auth.user.userId,
                "user": null,
                "recipes": null,
                "private": false
            })
        }).then((response) => {
            if (response.status === 201) {
                getCookBooks()
            } else {
                console.log(response)
            }
        });
    };

    const handleUserEditSubmit = (event) => {
        const u = {                
            userId: user.userId,
            userName: `${userName ? userName : user.userName}`,
            email: `${email ? email : user.email}`,
            passwordHash: 'password',
            firstName: `${firstName !== '' ? firstName : user.firstName}`,
            lastName: `${lastName !== '' ? lastName : user.lastName}`,
            role: user.role,
            userRoleId: user.userRoleId,
            active: `${isActive}`,
        };

        console.log(u);

        event.preventDefault();
        fetch(`http://localhost:8080/api/user/${user.userId}`, {
            method: 'PUT',
            headers: {'Content-Type':'application/json'},
            body: JSON.stringify({
                userId: user.userId,
                userName: `${userName !== '' ? userName : user.userName}`,
                email: `${email !== '' ? email : user.email}`,
                passwordHash: 'password',
                firstName: `${firstName !== '' ? firstName : user.firstName}`,
                lastName: `${lastName !== '' ? lastName : user.lastName}`,
                role: user.role,
                userRoleId: user.userRoleId,
                active: isActive,
            })
        }).then((response) => {
            if (response.status === 204) {
                getUser();
                setEditUser(false);
            } else {
                response.json()
                    .then((data) => {
                        setErrors(data);
                    });
                console.log(response);
            }
        });
    }

    const deleteRecipe = (recipe) => {
        if (window.confirm("You are about to delete a recipe. Are you sure?")) {
            fetch(`http://localhost:8080/api/recipe/${recipe.recipeId}`, {
                method: 'delete'})
                .then((response) => {
                    if (response.status >= 400) {
                        history.push("/notfound");
                    } else {
                        getRecipes();
                    }
                }
            )
        };
    };

    const deactivateUser = () => {
        if (window.confirm("You are about to " + `${isActive ? "deactivate" : "activate"}` + " your account? Are you sure?")) {
            if (isActive) {
                setIsActive(false);
                auth.logout();
                history.push("/");
            } else {
                setIsActive(true);
            }
        }
    }
    
    // useEffect(() => {
    //     setEmail();
    //     setFirstName();
    //     setLastName();
    //     setCookBook();
    //     setUserName();
    // });    

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
                            {/* <td>{user.userName}</td> */}
                            <td>
                                {editUser===false ? `${user.userName}` : 
                                    <input type="text" placeholder={user.userName} onChange={event => setUserName(event.target.value)}></input>
                                }
                            </td>
                        </tr>
                        <tr>
                            <th>Email</th>
                            {/* <td>{user.email}</td> */}
                            <td>
                                {editUser===false ? `${user.email}` : 
                                    <input type="email" placeholder={user.email} onChange={event => setEmail(event.target.value)}></input>
                                }
                            </td>
                        </tr>
                        <tr>
                            <th>First Name</th>
                            <td>
                                {editUser===false ? `${user.firstName}` : 
                                    <input type="text" placeholder={user.firstName} onChange={event => setFirstName(event.target.value)}></input>
                                }
                            </td>
                        </tr>
                        <tr>
                            <th>Last Name</th>
                            <td>
                                {editUser===false ? `${user.lastName}` : 
                                    <input type="text" placeholder={user.lastName} onChange={event => setLastName(event.target.value)}></input>
                                }
                            </td>
                        </tr>
                        <tr>
                            <th>Status</th>
                            {/* <td>{!auth.user.status ? 'active': 'deactivated'}</td> */}
                            <td>
                                {editUser === false ? (isActive ? 'active' : 'deactivated') :
                                    <button onClick={(event) => deactivateUser()}>
                                        {isActive ? 'Deactivate Account' : 'Activate Account'}
                                    </button>
                                }
                            </td>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <Errors errors={errors}/>
                            </td>
                        </tr>
                        {/* {
                            editUser && (
                                <tr>
                                    <th colspan={2} className="text-center">Enter your password below to confirm:</th>
                                </tr>
                            )
                        }
                        {
                            editUser && (
                                <tr>
                                    <th>Password</th>
                                    <td clospan={2}>
                                        <input type="password" placeholder="New Last Name Here..." onChange={event => setPassword(event.target.value)}></input>
                                    </td>
                                </tr>
                            )
                        } */}

 
                        <tr>
                            <td colspan={editUser === true ? 1 : 2} className="text-center">
                                <button className="btn btn-secondary" onClick={() => editUser===false ? setEditUser(true): setEditUser(false) && getUser()}>{editUser===false ? 'Edit': 'Cancel'}</button>
                            </td>
                            {editUser && (
                                <td>
                                    <button className="btn btn-info" type='submit' onClick={(event) => handleUserEditSubmit(event)}>Submit</button>
                                </td>

                            )}
                        </tr>
                    </table>
                </div>

                <div className='col-lg-8 col-md-12 col-sm-12 mb-4'>

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
                                        <button type="button" onClick={(event) => deleteCookBook(book)} className="btn btn-danger ml-auto mr-auto">Delete Cookbook</button>
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
                            <button className="btn btn-secondary ml-2" type="submit" onClick={event => createCookBook(event)}>Add Now!</button>
                        </form>
                    </div>
                </Collapse>



                <Accordion className="mb-1 mt-4" defaultActiveKey="0">
                    <h3 className="m-2">Your Recipes:</h3>

                    {
                        recipes.map((recipe) => (
                            <Card>
                                <Card.Header>
                                <Accordion.Toggle as={Button} variant="link" eventKey="0">
                                    {recipe.name}
                                </Accordion.Toggle>
                                </Card.Header>
                                <Accordion.Collapse eventKey="0">
                                <Card.Body>
                                    <div className="row mb-4">
                                        <div className="col-3">
                                            <Link className="dark" to={`/recipe/${recipe.recipeId}`}>
                                                <img src={recipe.imageLink} height='75'></img>
                                            </Link>
                                        </div>
                                        <div className="col-4">
                                            <Link className="dark" to={`/recipe/${recipe.recipeId}`}>
                                                <div>{recipe.name}</div>
                                                <div>{<Rating detailed={false} reviews={recipe.reviews} />}</div>
                                                
                                            </Link>
                                        </div>
                                        <div className="col-4">
                                            <Link to={`recipe/${recipe.recipeId}`}>
                                                <button className="btn btn-info mr-2 ml-2">View</button>
                                            </Link>
                                            <button className="btn btn-danger ml-2 mr-2" onClick={() => deleteRecipe(recipe)}>Delete</button>
                                        </div>
                                    </div>
                                </Card.Body>
                                </Accordion.Collapse>
                            </Card>
                        ))
                    }
                </Accordion>
                    <Link to="/post"><button className="btn btn-secondary m-2">Create Recipe</button></Link>
                </div>
            </div>
        </div>    
    );
}