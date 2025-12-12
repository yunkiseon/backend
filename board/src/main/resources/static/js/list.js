// 검색 폼 submit 시
const form = document.querySelector("#actionForm");
form.addEventListener("submit", (e) => {
  // submit 중지
  e.preventDefault();
  // keyword, select 값 있나 확인
  // 없다면 메세지 띄우기
  if (form.type.value === "") {
    alert("검색 타입을 선택하세요");
    return;
  } else if (form.keyword.value === "") {
    alert("검색어를 입력하세요");
    return;
  }
  //page 값을 1로 변경
  form.page.value = 1;
  form.submit();
  //action 값을 안정해놔서 주소줄로 간다 -> controller/list 로 간다
  //이미 dto를 넣어놓았기 때문에 해당에 맞게 getlist가 적용이 된다.(주소는 get)
});
