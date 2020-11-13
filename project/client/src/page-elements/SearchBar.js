import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import { useHistory } from 'react-router-dom';

function SearchBar() {
    const [search, setSearch] = useState();

    const history = useHistory(); 

    const searchTerm = (event) => {
        setSearch(event.target.value)
        console.log(search)
    }

    useEffect(() => {
        
    }, []);

    // const searchAction = (event) => {

    // }

    return (
        <div>
            <form>
                <div className="text-left">
                    <i className="material-icons mdc-button__icon">search</i>
                </div>
                <i className="material-icons mdc-button__icon hidden">search</i>
                <input className="form-control" type="text" placeholder="Search" onChange={searchTerm}></input> 
            </form>
        </div>
    );
}
  
export default SearchBar;