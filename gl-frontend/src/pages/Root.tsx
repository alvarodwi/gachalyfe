import { Link } from "react-router-dom";

export default function Root() {
  return (
    <>
      <h1>GachaLyfe</h1>
      <Link to={"/rapi"}>
        <p>Rapi</p>
      </Link>
      <br />
      <Link to={"/sonetto"}>
        <p>Sonetto</p>
      </Link>
    </>
  );
}
