const url = `http://localhost:8080/memo`;
const form = document.querySelector("#modify-form");

// 만약 Uncaught TypeError 가 뜬다면 잘못된 querySelector을 지정한 것이다.
// Controller 잘못으론 Ambigious handler methos mapped for~ 에러가 있다. 같은 경로가
// 여러 개 있을 때 나는 에러이다. Controller 가 달라도 같은 경로이면 안된다.
// template Resolver 오류도 동일하게 Controller 오류다. 이 때는 경로와 매칭되는 template 파일이 있나
// 확인해야 한다.
// 404 에러도 동일. Controller 에서 경로 Mapping 확인해야하다.

// 삭제 클릭시
document.querySelector(".btn-outline-danger").addEventListener("click", (e) => {
  const id = form.id.value;

  fetch(`http://localhost:8080/memo/${id}`, {
    method: "DELETE",
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`error! ${res.status}`);
      }
      //json 바디 추출 id 가 아닌 String을 보낸 것이기 때문에
      // json이 아니라 text로 해야한다.
      return res.text();
    })
    .then((data) => {
      console.log(data);
      if (data) {
        Swal.fire({
          title: "데이터 삭제 완료",
          icon: "success",
          draggable: true,
        });
      }
      // 이동
      location.href = "/memp/list2";
    })
    .catch((err) => console.log(err));
});

form.addEventListener("submit", (e) => {
  e.preventDefault();

  // 스크립트 객체
  const send = {
    id: form.id.value,
    text: form.text.value,
  };

  console.log(send);

  fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(send), // 자바스크립트 객체를 json으로 바꾸어주는 함수
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
          title: "데이터 수정 완료",
          icon: "success",
          draggable: true,
        });
      }
      // location.reload();
    })
    .catch((err) => console.log(err));
});
