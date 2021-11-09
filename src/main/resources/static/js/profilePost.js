$(document).ready(function () {
    getMyPostByPage();
    $(window).scroll(function () {
        if ($(window).scrollTop() == $(document).height() - $(window).height()) {
            LoadMoreToggleProfile();
        }
    });
})
