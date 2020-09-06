function addRandomInput(){
    // Number of inputs to create
    var number = document.getElementById("test").value;
    // Container <div> where dynamic content will be placed
    var container = document.getElementById("addedForms");
    // Clear previous contents of the container

    container.appendChild(document.createElement("tr"));

    var td1 = document.createElement("td");
    td1.innerText = number;
    container.appendChild(td1);
    var td2 = document.createElement("td");
    td2.innerText = 100;
    container.appendChild(td2);
    container.appendChild(document.createElement("br"));

    // for (i=0;i<number;i++){
    //     // Append a node with a random text
    //     container.appendChild(document.createTextNode("Member " + (i+1)));
    //     // Create an <input> element, set its type and name attributes
    //     var input = document.createElement("input");
    //     input.type = "text";
    //     input.name = "member" + i;
    //     container.appendChild(input);
    //     // Append a line break
    //     container.appendChild(document.createElement("br"));
    // }
}