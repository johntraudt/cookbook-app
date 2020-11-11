import React from 'react';

function SearchBar() {
    return (
        <div>
            <form>
                <div className="text-left">
                    <i class="material-icons mdc-button__icon" for="searchBar">search</i>
                </div>
                <i class="material-icons mdc-button__icon hidden" for="searchBar">search</i>
                <input class="form-control" type="text" id="searchBar" placeholder="Search" aria-label="Search"></input>
            </form>
        </div>
    );
}
  
export default SearchBar;