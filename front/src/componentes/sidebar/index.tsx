import { Link } from "react-router-dom";

function Sidebar() {

    return (

      <div className="bg-dark text-white vh-100 p-3">
        <div className="mb-4 text-center">
          <img
            src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAABTUlEQVR4nO3XQQ6DIBAF0SfE+T+W5B0iOwS6awtxkwU2zeYZPSNjBR6fOHpAgQIECBAgAABAj+A54D8CtwDfwFbgEewBX4DPwFvgE+wBXwBP4DtwDXwFvgG/wIXAFvAG/gGvwFb4B78BW+AX/ABfAF/gIXAFvAG/gGvwFb4B78BW+AX/ABfAF/gIXAFvAG/gGvwFb4B78BefrQp3Wpmh7UeP6Z9v7ce+XH4jctbeEv1yZBgQIECBAgAABAv+TfgCKUvUus5VxvAAAAABJRU5ErkJggg=="
            alt="logo"
            className="img-fluid rounded"
          />
        </div>
        <ul className="list-unstyled">
          <li className="mb-2">
            <a href="/" className="text-white text-decoration-none d-block px-2 py-1 rounded">Home</a>
          </li>
          <li>
            <a
              href="#submenucadastro"
              data-bs-toggle="collapse"
              aria-expanded="false"
              aria-controls="submenucadastro"
              className="text-white text-decoration-none d-block px-2 py-1 rounded"
            >
              Cadastro
            </a>
            <ul className="collapse list-unstyled ps-3 mt-2" id="submenucadastro">
              <li className="mb-1">
                <a href="/usuario" className="text-white text-decoration-none d-block px-2 py-1 rounded">Usuário</a>
              </li>
              <li>
                <Link to="/usuario" className="text-white text-decoration-none d-block px-2 py-1 rounded">Usuário</Link>
              </li>
              <li>
                <Link to="/carrinho" className="text-white text-decoration-none d-block px-2 py-1 rounded">Carrinho</Link>
              </li>
            </ul>
          </li>
        </ul>
      </div>
      
    );
  }
  
  export default Sidebar;
  