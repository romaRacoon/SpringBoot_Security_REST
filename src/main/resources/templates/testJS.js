fetch('http://localhost:8080/admin/users').then(
    res=>{
        res.json().then(
            data=>{
                console.log(data);
                if (data.length>0){
                    var temp="";
                    data.forEach((u)=>{
                        temp+="<tr>";
                        temp+="<td>"+u.firstName+"</td>>";
                        temp+="<td>"+u.secondName+"</td>>";
                        temp+="<td>"+u.age+"</td></tr>";
                    })
                    document.getElementById("data").innerHTML=temp;
                }
            }
        )
    }
);