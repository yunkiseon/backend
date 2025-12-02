// 삭제버튼 클릭시 remove form 가져와서 submit 시키기
document.querySelector(".btn-outline-danger").addEventListener("click", () => {
  document.querySelector("[name='remove-form']").submit();
});

