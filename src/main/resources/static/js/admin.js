const urlOfAllUsers='http://localhost:8080/admin/allUsers';
const urlOfGetUserById='http://localhost:8080/admin/getUser/';
const urlOfEditUserById='http://localhost:8080/admin/edit'
const testUrl='http://localhost:8080/admin/test'
var tempForAdmin = 0;
var allRoles = [];
var roleAdmin = '';
var roleUser = '';

// Получение всех юзеров и заполнение таблицы на странице админа
$.ajax(urlOfAllUsers,{
    success: function (){
        fetch(urlOfAllUsers).then(
            res => {
                res.json().then(
                    data => {
                        if (data.length > 0) {
                            var allRolesTemp = "";
                            var temp = "";

                            data.forEach((u) => {
                                tempForAdmin++;
                                var editButtonId = u.id;
                                var deleteButtonId = u.id;
                                temp += "<tr>";
                                temp += "<td>"+u.id+"</td>";
                                temp += "<td>"+u.name+"</td>";
                                temp += "<td>"+u.lastName+"</td>";
                                temp += "<td>"+u.age+"</td>";
                                temp += "<td>"+u.username+"</td>";
                                temp += "<td>";
                                $.each(u.roles, function (key, val) {
                                    temp += val.name.slice(5) + " ";
                                    allRoles.push(val.name.slice(5));
                                });
                                temp += "</td>";
                                temp += "<td><button type=\"button\" class=\"btn btn-primary\" id='editButton' editButton='e"+editButtonId.toString()+"'"+" data-toggle='modal' data-target='#editModal'>Edit</button></td>";
                                temp += "<td><button type=\"button\" class=\"btn btn-danger\" id='deleteButton' deleteButton='d"+deleteButtonId.toString()+"'"+" data-toggle='modal' data-target='#deleteModal'>Delete</button></td></tr>";
                                editButtonId++;
                                deleteButtonId++;
                            })

                            tempForAdmin++;
                            if (tempForAdmin == 1) {
                                $('#userLink').hide();
                            }
                            var tempForRoles = 1;
                            fetch('http://localhost:8080/admin/getAllRoles').then(
                                roles => {
                                    roles.json().then(
                                        myData => {
                                            myData.forEach((u) => {
                                                if (tempForRoles == 1) {
                                                    roleUser += u.name;
                                                } else {
                                                    roleAdmin += u.name;
                                                }
                                                tempForRoles++;
                                            })
                                            $('#addRole').prepend('<option value="1">'+'ADMIN'+'</option>');
                                            $('#addRole').prepend('<option value="2">'+'USER'+'</option>');
                                        }
                                    )
                                }
                            )
                            document.getElementById("tableAllUsers").innerHTML = temp;
                        }
                    }
                )
            }
        )
    }
});

//Нажат ли крестик в модальном окне edit
$(document).on('click','#editCloseButton',function() {
    $('#editName').val('');
    $('#editLastName').val('');
    $('#editPhoneNumber').val('');
    $('#editEmail').val('');
    $('#editPassword').val('');
})

// Нажата ли кнопка edit
$(document).on('click','#editButton',function(){
    var buttonId = $(this).attr('editButton').slice(1);
    var urlEdit = urlOfGetUserById+buttonId;
    $.ajax(urlEdit, {
        success: function (data) {
            $('#editId').attr('placeholder', data.id);
            $('#editName').val($('#editName').val() + data.name);
            $('#editLastName').val($('#editLastName').val() + data.lastName);
            $('#editPhoneNumber').val($('#editPhoneNumber').val() + data.age);
            $('#editEmail').val($('#editEmail').val() + data.username);
            $('#editPassword').val($('#editPassword').val() + data.password);

            $('#editRole').append($('<option>', {
                value: 1,
                text: 'USER'
            }));

            $(document).on('click', '#editButtonForever', function (){
                let editUser = {
                    id: $('#editId').attr('placeholder'),
                    name:$("input[name='username']").val(),
                    lastName: $("input[name='lastName']").val(),
                    age: $("input[name='phoneNumber']").val(),
                    username: $("input[name='email']").val(),
                    password: $("input[name='password']").val(),
                    roles : getUserRolesForEdit()
                }
                console.log(editUser);
                $.ajax({
                    url:urlOfEditUserById,
                    //url:testUrl,
                    type:'PUT',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    data: JSON.stringify(editUser),
                    success: function (result) {
                        $('#tableAllUsers').empty();
                        $.ajax(urlOfAllUsers,{
                            success: function (){
                                fetch(urlOfAllUsers).then(
                                    res => {
                                        res.json().then(
                                            data => {
                                                if (data.length > 0) {

                                                    var temp = "";

                                                    data.forEach((u) => {
                                                        var editButtonId = u.id;
                                                        var deleteButtonId = u.id;
                                                        temp += "<tr>";
                                                        temp += "<td>"+u.id+"</td>";
                                                        temp += "<td>"+u.name+"</td>";
                                                        temp += "<td>"+u.lastName+"</td>";
                                                        temp += "<td>"+u.age+"</td>";
                                                        temp += "<td>"+u.username+"</td>";
                                                        temp += "<td>"+u.roles[0].name.slice(5)+"</td>";
                                                        temp += "<td><button type=\"button\" class=\"btn btn-primary\" id='editButton' editButton='e"+editButtonId.toString()+"'"+" data-toggle='modal' data-target='#editModal'>Edit</button></td>";
                                                        temp += "<td><button type=\"button\" class=\"btn btn-danger\" id='deleteButton' deleteButton='d"+deleteButtonId.toString()+"'"+" data-toggle='modal' data-target='#deleteModal'>Delete</button></td></tr>";
                                                    })
                                                    document.getElementById("tableAllUsers").innerHTML = temp;
                                                }
                                            }
                                        )
                                    }
                                )
                            }
                        });
                        $('#editCloseButton').trigger('click');
                        $('#editName').val('');
                        $('#editLastName').val('');
                        $('#editPhoneNumber').val('');
                        $('#editEmail').val('');
                        $('#editPassword').val('');
                    },
                    error: () => {
                        console.log(editUser);
                        console.log(2);
                    }
                });
            });
        }
    });
});

function getUserRolesForEdit() {
    var allRoles = [];
    $("#editRole > option").each(function() {
        var role = {}
        role.id = this.value;
        role.name = 'ROLE_'+this.text;
        allRoles.push(role);
    });
    console.log(allRoles);
    return allRoles;
}

function getUserRolesForAdd() {
    var allRoles = [];
    $("#addRole option:selected").each(function () {
        console.log(this.text);
        var role = {};
        role.id = $(this).val();
        role.name = "ROLE_"+this.text;
        allRoles.push(role);
    });

    return allRoles;
}

//Нажата ли кнопка delete
$(document).on('click','#deleteButton',function(){
    var editButton = $(this).attr('deleteButton');
    console.log(urlOfGetUserById+editButton.slice(1));
    $.ajax(urlOfGetUserById+editButton.slice(1), {
        success: function(data) {
            var editUser = {
                id: $("input[name='id']").val(),
                username: $("input[name='username']").val(),
                lastName: $("input[name='lastName']").val(),
                phoneNumber: $("input[name='phoneNumber']").val(),
                email: $("input[name='email']").val(),
                password: $("input[name='password']").val(),
                roles: getUserRolesForEdit()

            }

            $('#delId').attr('placeholder', data.id);
            $('#delName').attr('placeholder', data.name);
            $('#delLastName').attr('placeholder', data.lastName);
            $('#delAge').attr('placeholder', data.age);
            $('#delEmail').attr('placeholder', data.username);
            $('#delRole').append('<option value="kia">'+data.roles[0].name.slice(5)+'</option>');
            if (data.roles.length > 1) {
                $('#delRole').append('<option value="kia">'+data.roles[1].name.slice(5)+'</option>');
            }

            //Нажата ли внутренняя кнопка delete
            $(document).on('click', '#deleteButtonForever', function () {
               $.ajax({
                   url:'http://localhost:8080/admin/delete/'+editButton.slice(1),
                   type:'DELETE',
                   success:function() {
                       $('#tableAllUsers').empty();
                       $.ajax(urlOfAllUsers,{
                           success: function (){
                               fetch(urlOfAllUsers).then(
                                   res => {
                                       res.json().then(
                                           data => {
                                               if (data.length > 0) {

                                                   var temp = "";

                                                   data.forEach((u) => {
                                                       var editButtonId = u.id;
                                                       var deleteButtonId = u.id;
                                                       temp += "<tr>";
                                                       temp += "<td>"+u.id+"</td>";
                                                       temp += "<td>"+u.name+"</td>";
                                                       temp += "<td>"+u.lastName+"</td>";
                                                       temp += "<td>"+u.age+"</td>";
                                                       temp += "<td>"+u.username+"</td>";
                                                       temp += "<td>"+u.roles[0].name.slice(5)+"</td>";
                                                       temp += "<td><button type=\"button\" class=\"btn btn-primary\" id='editButton' editButton='e"+editButtonId.toString()+"'"+" data-toggle='modal' data-target='#editModal'>Edit</button></td>";
                                                       temp += "<td><button type=\"button\" class=\"btn btn-danger\" id='deleteButton' deleteButton='d"+deleteButtonId.toString()+"'"+" data-toggle='modal' data-target='#deleteModal'>Delete</button></td></tr>";
                                                   })
                                                   document.getElementById("tableAllUsers").innerHTML = temp;
                                               }
                                           }
                                       )
                                   }
                               )
                           }
                       });
                       $('#deleteCloseWindowButton').trigger('click');
                   }
               })
            });
        }
    });
});

//Нажата ли кнопка addNewUser
$(document).on('click', '#addNewUserButton', function () {
    let newUser = {
        name : $('#name').val(),
        lastName : $('#lastName').val(),
        age : $('#phoneNumber').val(),
        username : $('#email').val(),
        password : $('#password').val(),
        roles : getUserRolesForAdd()
    }
    $.ajax({
        url: 'http://localhost:8080/admin/newUser',
        method: 'POST',
        dataType: 'json',
        data: JSON.stringify(newUser),
        contentType: 'application/json; charset=utf-8',
        success: function () {
            alert("Пользователь добавлен")
            $('#tableAllUsers').empty();
            $.ajax(urlOfAllUsers,{
                success: function (){
                    fetch(urlOfAllUsers).then(
                        res => {
                            res.json().then(
                                data => {
                                    if (data.length > 0) {

                                        var temp = "";

                                        data.forEach((u) => {
                                            tempForAdmin++;
                                            var editButtonId = u.id;
                                            var deleteButtonId = u.id;
                                            temp += "<tr>";
                                            temp += "<td>"+u.id+"</td>";
                                            temp += "<td>"+u.name+"</td>";
                                            temp += "<td>"+u.lastName+"</td>";
                                            temp += "<td>"+u.age+"</td>";
                                            temp += "<td>"+u.username+"</td>";
                                            temp += "<td>"+u.roles[0].name.slice(5)+"</td>";
                                            temp += "<td><button type=\"button\" class=\"btn btn-primary\" id='editButton' editButton='e"+editButtonId.toString()+"'"+" data-toggle='modal' data-target='#editModal'>Edit</button></td>";
                                            temp += "<td><button type=\"button\" class=\"btn btn-danger\" id='deleteButton' deleteButton='d"+deleteButtonId.toString()+"'"+" data-toggle='modal' data-target='#deleteModal'>Delete</button></td></tr>";
                                        })

                                        if (tempForAdmin == 1) {
                                            $('#userLink').hide();
                                        }

                                        document.getElementById("tableAllUsers").innerHTML = temp;
                                    }
                                }
                            )
                        }
                    )
                }
            });
            $('#admin-tab').tab('show');
        },
        error: function () {
            alert('error addUser')
        }
    });
});


