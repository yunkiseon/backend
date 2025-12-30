const imgModal = document.getElementById("imgModal");
if (imgModal) {
  imgModal.addEventListener("show.bs.modal", (e) => {
    // 모달을 뜨게 한 li 요소 찾기
    const posterLi = e.relatedTarget;
    // li의 data-* 요소 값 가져오기
    const filePath = posterLi.getAttribute("data-file");
    // If necessary, you could initiate an Ajax request here
    // and then do the updating in a callback.

    // Update the modal's content.
    const modalTitle = imgModal.querySelector(".modal-title");
    const modalBody = imgModal.querySelector(".modal-body");

    modalTitle.textContent = `${title}`;
    modalBody.innerHTML = `<img src="/upload/display?fileName=${filePath}" style="width:100%"></img>`;
  });
}
