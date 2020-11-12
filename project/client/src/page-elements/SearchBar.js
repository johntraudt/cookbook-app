import React, {useState} from 'react';
import { Link } from 'react-router-dom';
import { useHistory } from 'react-router-dom';

function SearchBar() {
    const [search, setSearch] = useState();

    const history = useHistory(); 

    const searchAction = (event) => {
        
        // history.push(`/{search}`)
    }

    return (
        <div>
            <form onSubmit={searchAction()}>
                <div className="text-left">
                    <i className="material-icons mdc-button__icon">search</i>
                </div>
                <i className="material-icons mdc-button__icon hidden">search</i>
                <input className="form-control" type="text" placeholder="Search" value={search}></input> 
            </form>
        </div>
    );
}
  
export default SearchBar;