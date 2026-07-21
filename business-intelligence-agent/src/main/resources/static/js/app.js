async function askQuestion() {

    const input = document.getElementById("question");

    const question = input.value.trim();

    if(question===""){
        return;
    }

    const chatBox=document.getElementById("chatBox");

    chatBox.innerHTML +=
        "<div class='user'>"+question+"</div>";

    input.value="";

    chatBox.innerHTML +=
        "<div class='bot' id='loading'>Thinking...</div>";

    chatBox.scrollTop=chatBox.scrollHeight;

    const response = await fetch("/api/chat",{

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body:JSON.stringify({

            question:question

        })

    });

    const answer = await response.text();

    document.getElementById("loading").remove();

    chatBox.innerHTML +=
        "<div class='bot'>"+answer+"</div>";

    chatBox.scrollTop=chatBox.scrollHeight;

}

document
.getElementById("question")
.addEventListener("keypress",function(e){

    if(e.key==="Enter"){

        askQuestion();

    }

});