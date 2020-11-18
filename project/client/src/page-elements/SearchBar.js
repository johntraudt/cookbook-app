import React, {useState, useEffect} from 'react';
import { useHistory } from 'react-router-dom';

function SearchBar() {
    const [search, setSearch] = useState('');

    const history = useHistory(); 

    useEffect(() => {
        setSearch()
    },[]);

    return (
        <div>
            <form onSubmit={event => history.push(`/results/${search}`)}>
                <div className="text-left">
                    <i className="material-icons mdc-button__icon">search</i>
                </div>
                <i className="material-icons mdc-button__icon hidden">search</i>
                <input className="form-control" type="text" placeholder="Search" onChange={event => setSearch(event.target.value)}></input> 
            </form>
        </div>
    );
}
  
export default SearchBar;