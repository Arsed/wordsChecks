var tabel=document.querySelector(".tableStyle");
var headers=document.querySelectorAll(".header span");
completeTable();

window.addEventListener('resize', calculateWidth, false);

function completeTable(){

  var req = $.getJSON("assets/storeData.json");

  req.success(function(json){
    for(var j=0;j<json.length;j++){
       var tr=document.createElement('tr');
       tabel.append(tr);
       completeRow(json[j],tr);
       tr.setAttribute("class","rowStyle");
       tr.setAttribute("index",j);
  }
  calculateWidth();
});
}


function completeRow(text,tr)
{
  completeCell(text.firstName+" "+text.lastName,tr);
  completeCell(text.type,tr);
  completeCell(text.steps,tr);
  completeParticipants(text.participants,tr);
  completeCell(text.tasks,tr);
  completeCell(text.events,tr);
  completeCell(text.tags,tr);

}

function completeCell(text,cell){
  var type=document.createElement('td');
  type.innerHTML=text;
  cell.append(type);
}


function calculateWidth() {

    var headers=document.querySelectorAll(".header span");
    for(var i=0;i<headers.length;i++){
      var length=headers[i].clientWidth;
      var element=document.querySelectorAll("tr td:nth-child("+(i+1)+")");

      for(var j=0;j<element.length;j++)
        element[j].style.width=length+5-i/1.5+'px';
}
}


function completeParticipants(text,cell){

var contor=-5;
var avatarsTd=document.createElement('td');
 for(var i=0;i<text.length;i++){

    if(i==5){

        //more than 5 elements
        var newdiv=document.createElement('div');
        newdiv.setAttribute("class","image extraAvatars");

        newdiv.innerHTML='+'+(text.length-5);
        avatarsTd.append(newdiv);
        newdiv.setAttribute("onclick","extendDiv(event)");
          // newdiv.setAttribute("onmouseleave","hideTooltip(event)");
      }

      if(i<5)
       {
         //if we have less than 5 elements on the screen
         var newdiv=document.createElement('div');
         newdiv.style.backgroundImage=text[i].avatar;
         newdiv.setAttribute("class","image");
         newdiv.setAttribute("index",contor+5);
         contor++;
         newdiv.setAttribute("onmouseover","extendDiv(event)");
      //   newdiv.setAttribute("onmouseleave","hideTooltip(event)");
         avatarsTd.append(newdiv);
       }
  }
cell.append(avatarsTd);
}

function extendDiv(event){

  $.getJSON("assets/storeData.json", function(json) {

  var outTooltip=document.querySelector(".additionInfoContainer");
  tooltip =document.querySelector(".divInTooltip");
  lastChild = event.path[0];


  if(outTooltip.style.display=='none'){


var rect = lastChild.getBoundingClientRect();
outTooltip.style.top=(rect.top)+50+'px';
outTooltip.style.left=(rect.left) +'px';
outTooltip.classList.add("leftArrow");


    outTooltip.style.display="block";
    //verify and adding new elements in the tooltip
    if (lastChild.className=="image extraAvatars"){
      outTooltip.classList.add("exAdditionInfoContainer");
      var row=event.path[2];
      rowIndex=row.getAttribute("index");
      var z=json[rowIndex].participants.length;
      for(var i=5;i<json.length;i++)
      addData(json[rowIndex].participants[i].firstname,json[rowIndex].participants[i].secondname,json[rowIndex].participants[i].avatar,json[rowIndex].participants[i].role,tooltip);
      outTooltip.append(tooltip);

    } else {

      var locIndex=event.srcElement.attributes.index.value;
      var row=event.path[2];
      rowIndex=row.getAttribute("index");

      addData(json[rowIndex].participants[locIndex].firstname,json[rowIndex].participants[locIndex].secondname,json[rowIndex].participants[locIndex].avatar,json[rowIndex].participants[locIndex].role,tooltip);
    }
  } else {
    outTooltip.style.display="none";
    clearTooltip();
  }
  });
}



function addData(firstName,lastName,url,role,element){
  var div=document.createElement('div');
  var image=document.createElement('div');
  var auxDiv=document.createElement('div');
  var span =document.createElement('div');
  var span2=document.createElement('div');

  image.style.backgroundImage=url;
  image.setAttribute("class","addItemStyle");
  div.append(image);

  span.innerHTML=firstName+' '+lastName;
  span.style.color="#14AAC0";
  auxDiv.append(span);
  span2.innerHTML="role: "+role;
  span2.style.color="#97A9B2";
  auxDiv.append(span2);
  auxDiv.style.display="inline-block";
  div.append(auxDiv);
  div.setAttribute("class","itemDiv");

  element.append(div);
}


function hideTooltip(event){
  console.log(event)
  var outTooltip=document.querySelector(".additionInfoContainer");

  outTooltip.style.display="none";
  clearTooltip();
outTooltip.classList.remove("exAdditionInfoContainer");
}
function clearTooltip(){
  tooltip.innerHTML="";
}
