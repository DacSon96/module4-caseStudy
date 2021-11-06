let currentUser = JSON.parse(localStorage.getItem('currentUser'));

function getUserById(){
    $.ajax({
        headers: {
            'Authorization': 'Bearer ' + currentUser.accessToken
        },
        url: `http://localhost:8080/users/${currentUser.id}`,
        type: 'GET',
        success: function (user) {
            let username = `<h3 class="my-username">${user.username}</h3>`;
            let cover = `<img src="${user.cover}" class="coverImage" alt="coverImage" style="height: 250px">`
            let avatar = `<img src="${user.avatar}" alt="avatarImage">`
            $('.my-username').html(username);
            $('.my-cover').html(cover);
            $('.my-avatar').html(avatar);
        }
    })
}
$(document).ready(function (){
    getUserById();
})