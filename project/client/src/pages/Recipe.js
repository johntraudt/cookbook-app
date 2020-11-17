import React, {useState, useEffect, useContext} from 'react';
import Rating from '../page-elements/Rating'
import { useLocation, useHistory } from 'react-router-dom'
import DropdownButton from 'react-bootstrap/DropdownButton';
import Dropdown from 'react-bootstrap/Dropdown'
import AuthContext from '../page-elements/AuthContext';
// import { PDFDownloadLink, Page, Text, View, Document, StyleSheet } from '@react-pdf/renderer';
// import DownloadButton from '../page-elements/DownloadButton';


export default function Recipe() {

    const [recipe, setRecipe] = useState({
        cookTimeInMinutes: 0,
        date: "",
        directions: [],
        featured: false,
        imageLink: "",
        ingredients: [],
        name: "",
        prepTimeInMinutes: 0,
        recipeId: 0,
        reviews: [],
        servings: 0,
        tags: null,
        user: {
            userId: 1,
            userName: "",
            email: "",
            passwordHash: null,
            firstName: "",
            lastName: "",
            role: {
                userRoleId: 0,
                name: ""
            },
            userId: 0,
            userName: "",
            userRoleId: 0},
        userId: 0,
        wasUpdated: false
    });

    const today = new Date();
    const [review, setReview] = useState({
        reviewId: 0,
        recipeId: 0,
        userId: 0,
        userName: "",
        comment: "",
        rating: 5,
        date:  `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`
    }) 

    const auth = useContext(AuthContext);
    const history = useHistory();
    const location = useLocation();
    console.log(location.pathname)

    
    useEffect(() => {
        const getRecipe = () => {
            fetch(`http://localhost:8080/api${location.pathname}`)
                .then
                .then(response => response.json())
                .then((data) => {
                    setRecipe(data);
                    console.log(data);
                });
        }
        getRecipe();
    }, [location.pathname]);

    useEffect(() => {
        let tempReview = review;
    });

    if(!recipe) {
        return null;
    }

    const addReview = (event) => {
        event.preventDefault();
    };

    const setComment = (text) => {
        const tempReview = {
            reviewId: review.reviewId,
            recipeId: review.recipeId,
            userId: review.userId,
            userName: review.userName,
            comment: text,
            rating: review.rating,
            date:  `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`
        }
        setReview(tempReview);
        console.log(review.rating);
        console.log(review.comment);
    }

    const setRating = (score) => {
        console.log(score);
        const tempReview = {
            reviewId: review.reviewId,
            recipeId: review.recipeId,
            userId: review.userId,
            userName: review.userName,
            comment: review.comment,
            rating: score,
            date:  `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`
        }
        setReview(tempReview);
        console.log("LOOK HERE DUMMY");
        console.log(review.rating);
        console.log(review.comment);
    }

    // const styles = StyleSheet.create({
    //     page: {
    //       flexDirection: 'row',
    //       backgroundColor: '#E4E4E4'
    //     },
    //     section: {
    //       margin: 10,
    //       padding: 10,
    //       flexGrow: 1
    //     }
    //   });

    
        // <Document className="center">
        //     <Page size="A4" style={styles.page}>
        //         <div className="container full-body text-center">
        //             <div>
        //                 <PDFDownloadLink className="dark" document={<MyDocument />} fileName="somename.pdf">
        //                     {({ blob, url, loading, error }) => (loading ? 'Loading document...' : 'Download this recipe as a pdf')}
        //                 </PDFDownloadLink>

        //             </div>
        //             <div className="mt-4">
        //                 <h2 className="display-3">{recipe.name}</h2>
        //                 <Rating detailed={true} reviews={recipe.reviews}/>
        //                 <div className="row">
        //                     <div className="col-lg-2 col-sm-0"></div>
        //                     <div className="col-lg-8 col-sm-12">
        //                         <div className="row justify-content-md-center m-2">
        //                             <div className="mr-2 ml-2">User: {`${recipe.user.firstName} ${recipe.user.lastName}`}</div>
        //                             <div className="mr-2 ml-2">{recipe.wasUpdated ? 'Posted': 'Edited'}: {recipe.date}</div>
        //                             <div className="mr-2 ml-2">Prep Time: {recipe.prepTimeInMinutes}</div>
        //                             <div className="mr-2 ml-2">Cook Time: {recipe.cookTimeInMinutes}</div>
        //                             <div className="mr-2 ml-2">Servings: {recipe.servings}</div>

        //                             {
        //                             recipe.tags !== null && (
        //                                 <div className="mr-2 ml-2">{`Tags:
        //                                     ${
        //                                         recipe.tags.map((tag) => {return (` ${tag.name}`).toLowerCase()})
        //                                     }`}
        //                                 </div>
        //                             )}
        //                         </div>

        //                         <div className="card border-white">
        //                             <img className="card-img-top p-2" src={recipe.imageLink} alt={`${recipe.name}`}></img>
        //                             <div className="card-body p-0">
        //                                 <form className="m-2">
        //                                     {auth.user && (
        //                                     <div className="col m-2">
        //                                         <select className="p-1" name="myCookbooks" id="myCookbooks">
        //                                             <option value="">--Add To Cookbook--</option>
        //                                             <option value="Dessert Book">Dessert Book</option>
        //                                             <option value="Mexican Food">Mexican Food</option>
        //                                         </select>
        //                                         <button className="btn btn-light btn-outline-dark btn-sm">Add Recipe</button>
        //                                     </div>
        //                                     )}
        //                                     {/* <div className="col">
        //                                         <lable className="text-secondary">Desired Servings: </lable>
        //                                         <input type="number"  placeholder="Set Serving Size #..."></input>
        //                                     </div> */}
        //                                 </form>
        //                                 <div className="text-left">
        //                                     <h4>Ingredients</h4>
        //                                     <ul>
        //                                         {
        //                                             recipe.ingredients.map((ingredient) => {
        //                                                 return <li>{ingredient.quantity ? ingredient.quantity : ''} {ingredient.measurementUnit.measurementUnitId !== 0 && ingredient.measurementUnit.measurementUnitId !== 5 ? ingredient.measurementUnit.name : ''} {ingredient.ingredient.name}</li>
        //                                             })
        //                                         }
        //                                     </ul>
        //                                     <h4>Directions</h4>
        //                                     <ol>
        //                                         {
        //                                             recipe.directions.map((direction) => {
        //                                                 return <li>{direction.text}</li>
        //                                             })
        //                                         }
        //                                     </ol>
        //                                 </div>
        //                             </div>
        //                         </div>
        //                     </div>
        //                 </div>
        //                 <div className="col-lg-2 col-sm-0"></div>
        //             </div>
        //         </div>
        //         <PDFDownloadLink className="dark" document={<MyDocument />} fileName="somename.pdf">
        //             {({ blob, url, loading, error }) => (loading ? 'Loading document...' : 'Download this recipe as a pdf')}
        //         </PDFDownloadLink>
        //     </Page>
        // </Document>
    // const MyDocument = () => (
    //     <Document className="center">
    //         <Page size="A4" style={styles.page}>
    //             <View style={styles.section}>
    //                 <Text>Section #1</Text>
    //             </View>
    //             <View style={styles.section}>
    //                 <Text>Section #2</Text>
    //             </View>
    //         </Page>
    //     </Document>
    // );

    // if (!MyDocument) {
    //     return null;
    // }

    return (
        <div className="container full-body text-center">
            {/* <div>
                <MyDocument/>
            </div> */}

            {/* <DownloadButton/> */}

            <div className="mt-4">
                <h2 className="display-3">{recipe.name}</h2>
                <Rating detailed={true} reviews={recipe.reviews}/>
                <div className="row">
                    <div className="col-lg-2 col-sm-0"></div>
                    <div className="col-lg-8 col-sm-12">
                        <div className="row justify-content-md-center m-2">
                            <div className="mr-2 ml-2">User: {`${recipe.user.firstName} ${recipe.user.lastName}`}</div>
                            <div className="mr-2 ml-2">{recipe.wasUpdated ? 'Posted': 'Edited'}: {recipe.date}</div>
                            <div className="mr-2 ml-2">Prep Time: {recipe.prepTimeInMinutes}</div>
                            <div className="mr-2 ml-2">Cook Time: {recipe.cookTimeInMinutes}</div>
                            <div className="mr-2 ml-2">Servings: {recipe.servings}</div>

                            {
                            recipe.tags !== null && (
                                <div className="mr-2 ml-2">{`Tags:
                                    ${
                                        recipe.tags.map((tag) => {return (` ${tag.name}`).toLowerCase()})
                                    }`}
                                </div>
                            )}
                        </div>

                        <div className="card border-white">
                            <img className="card-img-top p-2" src={recipe.imageLink} alt={`${recipe.name}`}></img>
                            <div className="card-body p-0">
                                <form className="m-2">
                                    {auth.user && (
                                    <div className="col m-2">
                                        <select className="p-1" name="myCookbooks" id="myCookbooks">
                                            <option value="">--Add To Cookbook--</option>
                                            <option value="Dessert Book">Dessert Book</option>
                                            <option value="Mexican Food">Mexican Food</option>
                                        </select>
                                        <button className="btn btn-light btn-outline-dark btn-sm">Add Recipe</button>
                                    </div>
                                    )}
                                    {/* <div className="col">
                                        <lable className="text-secondary">Desired Servings: </lable>
                                        <input type="number"  placeholder="Set Serving Size #..."></input>
                                    </div> */}
                                </form>
                                <div className="text-left">
                                    <h4>Ingredients</h4>
                                    <ul>
                                        {
                                            recipe.ingredients.map((ingredient) => {
                                                return <li>{ingredient.quantity ? ingredient.quantity : ''} {ingredient.measurementUnit.measurementUnitId !== 0 && ingredient.measurementUnit.measurementUnitId !== 5 ? ingredient.measurementUnit.name : ''} {ingredient.ingredient.name}</li>
                                            })
                                        }
                                    </ul>
                                    <h4>Directions</h4>
                                    <ol>
                                        {
                                            recipe.directions.map((direction) => {
                                                return <li>{direction.text}</li>
                                            })
                                        }
                                    </ol>
                                </div>
                            </div>
                        </div>
                        <div>
                            <table className="table text-left mt-4">
                                <tr className="text-center">
                                    <th colspan="2">Comments</th>
                                </tr>
                                { auth.user && (
                                    <div>
                                        <tr>
                                            <td colspan="2">
                                                <form className="text-center" onSubmit={(event) => addReview(event)}>
                                                    <textarea className="form-control" onChange={(event) => setComment(event.target.value)} value={review.comment}></textarea>
                                                </form>
                                            </td>
                                        </tr>
                                        <tr className="text-center mr-3">
                                            <td className="ml-auto">
                                                <DropdownButton alignRight title={"⭐".repeat(review.rating) + "☆".repeat(5-review.rating)} id="dropdown-menu-align-right" onSelect={setRating}>
                                                    <Dropdown.Item eventKey={1}>⭐☆☆☆☆</Dropdown.Item>
                                                    <Dropdown.Item eventKey={2}>⭐⭐☆☆☆</Dropdown.Item>
                                                    <Dropdown.Item eventKey={3}>⭐⭐⭐☆☆</Dropdown.Item>
                                                    <Dropdown.Item eventKey={4}>⭐⭐⭐⭐☆</Dropdown.Item>
                                                    <Dropdown.Item eventKey={5}>⭐⭐⭐⭐⭐</Dropdown.Item>
                                                </DropdownButton>
                                            </td>
                                            <td className="mr-auto">
                                                <button className="btn btn-outline-secondary ml-3" type="submit">Post a Review</button>
                                            </td>
                                        </tr>
                                    </div>
                                )}
                                <table className="table">
                                    {recipe.reviews.map((review) => {
                                        return <div>
                                            <th>{review.comment === null ? "" : review.user.firstName + review.user.lastName}</th>
                                            <tr>
                                                <div className="pl-5">{review.comment === null ? "" : review.comment}</div>
                                            </tr>
                                            <tr>
                                                <div className="pl-5 pb-3"> {review.comment === null ? "" : <Rating detailed={false} reviews={[review]}/>}</div>
                                            </tr>
                                        </div>
                                    })}
                                </table>
                            </table>
                        </div>
                    </div>
                </div>
                <div className="col-lg-2 col-sm-0"></div>
            </div>
        </div>
    )
}