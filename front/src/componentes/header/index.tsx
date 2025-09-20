

function Header() {
    return (
      <header className="bg-dark">
        <nav className="container d-flex justify-content-start gap-3 py-3">
          <a href="/" className="text-white text-decoration-none">Home</a>
          <a href="/usuarios" className="text-white text-decoration-none">Usuarios</a>
        </nav>
      </header>
    );
  }

export default Header;