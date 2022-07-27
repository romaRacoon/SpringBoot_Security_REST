const urlOfAllUsers='http://localhost:8080/user/getRolesAmount';
var tempForUser = 0;

$.ajax(urlOfAllUsers,{
    success: function (data){
        if (parseInt(data) > 1) {
            console.log(4)
            $('#userLinkInUserPage').before('<nav class="nav-item">\n' +
                '                <a class="nav-link" href="/admin">Admin</a>\n' +
                '            </nav>');
        }
    }
});