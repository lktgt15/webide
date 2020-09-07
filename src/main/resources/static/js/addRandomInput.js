$('#addformbtn').on('click',function(){
    // Number of inputs to create
    var number = document.getElementById("test").value;
    var minnum = document.getElementById("test2").value;
    var maxnum = document.getElementById("test3").value;
    // Container <div> where dynamic content will be placed
    var container = document.getElementById("addedForms");
    // Clear previous contents of the container

    console.log(minnum);
    console.log(maxnum);

    if(minnum == "" || maxnum == ""){
        alert("모든 값을 채워주셔야 합니다");
        return;
    }
    if(minnum > maxnum){
        alert("min값보다 max값이 크면 안됩니다");
        return;
    }

    var div = document.createElement("div");
    div.className="list-group list-group-horizontal-xl"

    var coldiv1 = document.createElement("div");
    coldiv1.className="list-group-item"
    var coldiv2 = document.createElement("div");
    coldiv2.className="list-group-item"
    var coldiv3 = document.createElement("div");
    coldiv3.className="list-group-item"

    var k = document.createElement("input");
    k.name = "k"
    k.className = "form-control-plaintext"
    k.readOnly = true
    k.value = number
    coldiv1.appendChild(k)

    var min = document.createElement("input");
    min.name = "min"
    min.className = "form-control-plaintext"
    min.readOnly = true
    min.value = minnum
    coldiv2.appendChild(min)

    var max = document.createElement("input");
    max.name = "max"
    max.className = "form-control-plaintext"
    max.readOnly = true
    max.value = maxnum
    coldiv3.appendChild(max)
    div.appendChild(coldiv1)
    div.appendChild(coldiv2)
    div.appendChild(coldiv3)
    container.appendChild(div)

    document.getElementById("test3").value=null;
    document.getElementById("test2").value=null;

    container.appendChild(document.createElement("br"));
})

