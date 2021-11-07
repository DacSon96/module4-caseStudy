let currentUser = JSON.parse(localStorage.getItem('currentUser'));

$(document).ready(function (){
    getUserById();
});

function getUserById(){
    $.ajax({
        headers: {
            'Authorization': 'Bearer ' + currentUser.accessToken
        },
        url: `http://localhost:8080/users/${currentUser.id}`,
        type: 'GET',
        success: function (user) {
            let username = `<h3 class="my-username">${user.username}</h3>`;
            let cover = `<img src="${user.cover}" alt="coverImage" class="coverImage" style="height: 300px">`
            let avatar = `<img src="${user.avatar}" alt="avatarImage" class="dashboard-img">`
            let intro = `<h4>intro</h4><p>${user.intro}</p><hr>`
            let information = `<a href="#"><i class="fas fa-briefcase"></i>
                              <p>${user.work}</p>
                          </a>
                          <a href="#"><i class="fas fa-home"></i>
                              <p>${user.address}</p>
                          </a>`
            $('.my-username').html(username);
            $('.my-cover').html(cover);
            $('.my-avatar').html(avatar);
            $('.intro-bio').html(intro);
            $('.background-details').html(information);
        }
    })
};

function editingForm(id) {
    let myModal = new bootstrap.Modal(document.getElementById('editingModal'));
    myModal.show();
    // $.ajax({
    //     headers: {
    //         'Authorization': 'Bearer ' + currentUser.accessToken
    //     },
    //     url: `http://localhost:8080/users/${id}`,
    //     type: 'GET',
    //     success: function (phone) {
    //         $('#edit-model').val(phone.model)
    //         $('#edit-price').val(phone.price)
    //         $('#edit-producer').val(phone.producer)
    //         $('#editPhoneInfo').click(function () {
    //             editPhoneInfo(id)
    //         });
    //     }
    // }).fail(function () {
    //     window.location.href = "user.html"
    // });
}
