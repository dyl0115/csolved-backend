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

// Change their values as needed
    urlParams.set(searchType, searchTypeValue); // update with the select box value
    urlParams.set(searchKeyword, searchKeywordValue); // update with the input box value

// Reflect the changes in the browser's URL
    const newUrl = window.location.pathname + '?' + urlParams.toString();
    window.history.replaceState({}, '', newUrl);

// Send a GET request to the updated URL
    fetch(newUrl, {
        method: 'GET',
        headers: {
            'Accept': 'text/html'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(html => {
            document.documentElement.innerHTML = html;
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

function sortPosts() {

    const urlParams = new URLSearchParams(window.location.search);
    const sortType = 'sortType';

    const sortTypeValue = document.getElementById('sort-select').value;

    // Change its value as needed
    urlParams.set('page', 1);
    urlParams.set(sortType, sortTypeValue); // update with the select box value

    // Reflect the changes in the browser's URL
    const newUrl = window.location.pathname + '?' + urlParams.toString();
    window.history.replaceState({}, '', newUrl);

    // Send a GET request to the updated URL
    fetch(newUrl, {
        method: 'GET',
        headers: {
            'Accept': 'text/html'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(html => {
            document.documentElement.innerHTML = html;
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

// function filterPosts(filterTypeValue) {
//     const urlParams = new URLSearchParams(window.location.search);
//
//     // Update the URL parameters
//     urlParams.set('filterType', 'CATEGORY'); // Use the passed filterTypeValue
//     urlParams.set('filterValue', filterTypeValue);
//
//     // Reflect the changes in the browser's URL
//     const newUrl = window.location.pathname + '?' + urlParams.toString();
//     window.history.replaceState({}, '', newUrl);
//
//     // Send a GET request to the updated URL
//     fetch(newUrl, {
//         method: 'GET',
//         headers: {
//             'Accept': 'text/html'
//         }
//     })
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Network response was not ok');
//             }
//             return response.text();
//         })
//         .then(html => {
//             document.documentElement.innerHTML = html;
//         })
//         .catch(error => {
//             console.error('There was a problem with the fetch operation:', error);
//         });
// }

function updatePage(page) {
    const url = new URL(window.location.href);
    url.searchParams.set('page', page);
    window.location.href = url.toString();
}