document.querySelector(".btn-danger").addEventListener("click", () => {
  document.querySelector("form").action = "/board/remove";
  document.querySelector("form").submit();
});
