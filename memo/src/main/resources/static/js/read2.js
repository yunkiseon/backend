const url = `http://localhost:8080/memo/${id}`;
const form = document.querySelector("#form");
//get일때
fetch(url)
  .then((res) => {
    if (!res.ok) {
      throw new Error(`error! ${res.status}`);
    }
    //json body 추출
    return res.json();
  })
  .then((data) => {
    console.log(data);
    form.id.value = data.id;
    form.text.value = data.text;
  })
  .catch((err) => console.log(err));
