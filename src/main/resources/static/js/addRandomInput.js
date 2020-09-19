$('#addformbtn').on('click',function(){
    var rangeminv = document.getElementById("rangemin").value*1;
    var rangemaxv = document.getElementById("rangemax").value*1;
    var kminv = document.getElementById("kmin").value*1;
    var kmaxv = document.getElementById("kmax").value*1;

    var container = document.getElementById("addedForms");

    console.log("rangemaxv"+rangemaxv+typeof(rangemaxv))
    console.log("rangeminv"+rangeminv)
    console.log("kminv"+kminv)
    console.log("kmaxv"+kmaxv)

    if(kminv === 0 || kmaxv === 0 || rangeminv === 0 || rangemaxv === 0){
        alert("모든 값을 채워주셔야 합니다");
        return;
    }
    if(rangeminv > rangemaxv || kminv > kmaxv){
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
    var coldiv4 = document.createElement("div");
    coldiv4.className="list-group-item"

    var kmin = document.createElement("input");
    kmin.name = "kmin"
    kmin.className = "form-control-plaintext"
    kmin.readOnly = true
    kmin.value = kminv
    coldiv1.appendChild(kmin)

    var kmax = document.createElement("input");
    kmax.name = "kmax"
    kmax.className = "form-control-plaintext"
    kmax.readOnly = true
    kmax.value = kmaxv
    coldiv2.appendChild(kmax)

    var rangemin = document.createElement("input");
    rangemin.name = "rangemin"
    rangemin.className = "form-control-plaintext"
    rangemin.readOnly = true
    rangemin.value = rangeminv
    coldiv3.appendChild(rangemin)

    var rangemax = document.createElement("input");
    rangemax.name = "rangemax"
    rangemax.className = "form-control-plaintext"
    rangemax.readOnly = true
    rangemax.value = rangemaxv
    coldiv4.appendChild(rangemax)



    div.appendChild(coldiv1)
    div.appendChild(coldiv2)
    div.appendChild(coldiv3)
    div.appendChild(coldiv4)
    container.appendChild(div)

    document.getElementById("kmin").value=null;
    document.getElementById("kmax").value=null;
    document.getElementById("rangemin").value=null;
    document.getElementById("rangemax").value=null;

    container.appendChild(document.createElement("br"));
})

