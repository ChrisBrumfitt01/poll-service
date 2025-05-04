import { RotatingLines } from "react-loader-spinner";
import './LoadingSpinner.css';

export default function LoadingSpinner() {
    return (
        <div className="loading-spinner-container">
            <RotatingLines
                className="spinner"
                strokeColor="grey"
                strokeWidth="5"
                animationDuration="0.75"
                width="96"s
                visible={true}
            />
        </div>
      );

}