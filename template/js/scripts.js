var text;

function wordsChecks(){
  text=document.querySelector("textarea").value;
  var countOriginalWord;
  var newWords;
  text = text.replace(/  +/g, ' ').replace(/\n/g, " ").replace(/[0-9]/g, "").trim();

  countOriginalWord=text.split(" ").length;

  orgLength=text.length;

  text=text.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');

  //replace space
  text=text.replace(/[" "]/g, "").match(/.{1,10}/g);

  newWords=text.length;

  for(var k=0;k<text.length;k++)
  text[k]=text[k].charAt(0).toUpperCase()+text[k].slice(1);

  for(var i=0;i<text.length;i++){

    var str=text[i];
    for(var j=0;j<str.length;j++){

      if(str[j].match(/[AEIOU]/)&&str.length>3)
      {
        var c=0;
        while((j+1)<str.length&&(!str[j+1].match(/[AEIOUaeiou]/)))
        {
          j++;
          c++;
        }
      }

      if(c>1&&(j+1)<str.length){
        str=str.slice(0,j+1)+str[j+1].toUpperCase()+str.slice(j+2);
        text[i]=str;
      }
    }
  }

  var str=text.toString();
  str=str.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');

  var numUpperVowel = str.length-str.replace(/[A E I O U]/g, '').length;

  var consonantes=str.replace(/[A E I O U a e i o u]/g, '').length;

  var result=document.querySelector(".result");
  result.innerHTML=(countOriginalWord+'-'+newWords+'-'+numUpperVowel+'-'+consonantes+'-'+orgLength);

}
