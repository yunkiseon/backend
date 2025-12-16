const url = `http://localhost:8080/memo`;
const form = document.querySelector("#insert-form");
form.addEventListener("submit", (e) => {
  e.preventDefault();
  // post(put)일때
  fetch(url, {
    // 기본이 get이여서 post라고 따로 선언해주어야함
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ text: e.target.text.value }), // e.target = form
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`error! ${res.status}`);
      }
      //json body 추출
      return res.json();
    })
    .then((data) => {
      console.log(data);
      if (data) {
        Swal.fire({
          title: "데이터 추가 완료",
          icon: "success",
          draggable: true,
        });
      }
      // location.reload();
    })
    .catch((err) => console.log(err));
});
