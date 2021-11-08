$(document).ready(function () {
    getPostByPage();
    $(window).scroll(function () {
        if ($(window).scrollTop() == $(document).height() - $(window).height()) {
            LoadMoreToggle();
        }
    });
})