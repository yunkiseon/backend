
// x 를 클릭 시 파일 삭제
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  console.log("이벤트 대상 ", e.target);
  // closese() : 가장가까운 부모 요소 찾기
  e.preventDefault();

  const aTag = e.target.closest("a");
  const li = e.target.closest("li");

  console.log("속성 값 ", aTag.getAttribute("href"));
  const href = aTag.getAttribute("href");
  // 컨트롤러로 요청 보내기

  const formData = new FormData();
  formData.append("fileName", href);
  fetch("/upload/remove", {
    method: "post",
    body: formData,
  })
    .then((res) => res.text())
    .then((data) => {
      console.log(data);
      // 화면에서 이미지 제거
      li.remove();
    });
});
