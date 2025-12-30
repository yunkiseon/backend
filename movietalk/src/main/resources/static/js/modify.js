// x 를 클릭 시 파일 삭제
document.querySelectorAll(".uploadResult i").forEach((item) => {
  item.addEventListener("click", (e) => {
    console.log("이벤트 대상 ", e.target);
    // closese() : 가장가까운 부모 요소 찾기
    e.preventDefault();
    // 화면에서만 지울 것.
    // 이유: 수정값은 수정버튼 submit되면 db반영 혹은 파일 삭제를 하면 되는 것
    const li = e.target.closest("li");
    if (confirm("정말로 삭제하시겠습니까?")) {
      // 화면에서 이미지 제거
      li.remove();
    }
  });
});

// 삭제
document.querySelector(".delete").addEventListener("click", (e) => {
  e.preventDefault();
  // document.querySelector("#removeForm")
  const form = document.querySelector("#createForm");
  form.action = "/movie/remove";
  form.submit();
});
