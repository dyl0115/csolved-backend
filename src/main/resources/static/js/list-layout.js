/*!
* Start Bootstrap - Blog Home v5.0.9 (https://startbootstrap.com/template/blog-home)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-blog-home/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

function searchPosts() {

    const urlParams = new URLSearchParams(window.location.search);
    const searchType = 'searchType';
    const searchKeyword = 'searchKeyword';

    const searchTypeValue = document.getElementById('search-select').value;
    const searchKeywordValue = document.getElementById('search-input').value;

// Check if the query strings exist, if not add them
    if (!urlParams.has(searchType)) {
        urlParams.set(searchType, searchTypeValue); // set default value for searchType
    }

    if (!urlParams.has(searchKeyword)) {
        urlParams.set(searchKeyword, searchKeywordValue); // set default value for searchKeyword
    }

// Change their values as needed
    urlParams.set(searchType, searchTypeValue); // update with the select box value
    urlParams.set(searchKeyword, searchKeywordValue); // update with the input box value

// Reflect the changes in the browser's URL
    const newUrl = window.location.pathname + '?' + urlParams.toString();
    window.history.replaceState({}, '', newUrl);

// Send a GET request to the updated URL
    fetch(newUrl, {method: 'GET'})
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text(); // json() 대신 text() 사용
        })
        .then(html => {
            document.documentElement.innerHTML = html;
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}